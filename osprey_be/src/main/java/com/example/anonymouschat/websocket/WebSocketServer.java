package com.example.anonymouschat.websocket;

import com.alibaba.fastjson.JSON;
import com.example.anonymouschat.bean.SensitiveWordDetection;
import com.example.anonymouschat.constant.RedisEnum;
import com.example.anonymouschat.constant.SystemMsg;
import com.example.anonymouschat.mapper.UserNameMapper;
import com.example.anonymouschat.pojo.sql.TalkingMessage;
import com.example.anonymouschat.pojo.sql.UserName;
import com.example.anonymouschat.pojo.websocket.WebSocketSession;
import com.example.anonymouschat.pojo.result.http.Result;
import com.example.anonymouschat.pojo.result.websocket.WebSocketData;
import com.example.anonymouschat.pojo.receive.WebsocketReceive;
import com.example.anonymouschat.thread.AddTalkingMessageList;
import com.example.anonymouschat.beanUtil.SpringBeanUtil;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * WebSocket服务
 */
@Component
@ServerEndpoint("/ws/{sid}")
@DependsOn("springBeanUtil")
public class WebSocketServer{

    private UserNameMapper userNameMapper;
    private StringRedisTemplate stringRedisTemplate;

    //用于标记，判断Redis消息队列中是否存满100条
    private static Integer flagNumber = 0;

    //存放会话对象与名称
    private static Map<String, WebSocketSession> map = new HashMap();


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {

	System.out.println("客户端：" + sid + "建立连接");

	//处理未注册请求
	UserName userName = userNameMapper.getUserNameByUuid(sid);
	//如果uuid查询不到，设为未注册，非正常请求，断开链接
	if (userName == null || userName.getStatus() == 0){
	    try {
		//关闭链接
		session.close();
	    } catch (IOException e) {
		throw new RuntimeException(e);
	    }
	}

	//12.1日 加入顶号设置
	//查询到历史连接过的消息
	WebSocketSession webSocketSessionOld = map.get(sid);

	//判断是否存在已经连接的对话记录
	if (webSocketSessionOld != null){

	    Session sessionOld = webSocketSessionOld.getSession();

	    //存在发送提示消息，并断连
	    try {
		//发送消息
		sessionOld.getBasicRemote().sendText(JSON.toJSONString(WebSocketData.remote()));

		//断开连接
		sessionOld.close();

	    } catch (IOException e) {
		throw new RuntimeException(e);
	    }
	}

	//将本次连接对象加入List集合中
	WebSocketSession ws = WebSocketSession.builder()
		.name(userName.getRandomName())
		.session(session)
		.build();
	map.put(sid, ws);

	//发送心跳包（目的是为了传给前端一个在线人数）
	try {
	    session.getBasicRemote().sendText(JSON.toJSONString(WebSocketData.heart()));
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    //存储100条消息
    @OnMessage
    public void onMessage(String message, @PathParam("sid") String sid) {

	//输出发送消息信息
	System.out.println("收到来自客户端：" + sid + "的信息:" + message);
	//将收到的消息转化对象
	WebsocketReceive receive = JSON.parseObject(message, WebsocketReceive.class);

	//开始敏感词检测
	//检测到冷做返回值中data为700的状态
	if (SensitiveWordDetection.politicallySensitiveWordDetection(receive.getMsg())){

	    //获取session对象
	    Session session = map.get(sid).getSession();
	    try {
		//尝试发送信息
		session.getBasicRemote().sendText(JSON.toJSONString(WebSocketData.sensitiveWord()));
	    } catch (Exception e) {
		throw new RuntimeException(e);
	    }
	    return;
	}

	//判断状态码
	switch (receive.getCode()){
	    case 1:
		//判断1秒内是否发送过消息
		if (stringRedisTemplate.opsForValue().get("user:" + sid) != null){
		    //幂等验证不用返回消息，冷处理
		    break;
		}

		//设置幂等
		stringRedisTemplate.opsForValue().set("user:" + sid,"1", RedisEnum.ONE_SECONDS.getTime(),RedisEnum.ONE_SECONDS.getUnit());

		//TODO 状态码1为消息，需要进入队列缓存，并且同步转发

		//群发消息
		WebSocketData webSocketData = WebSocketData.builder()
			.msg(receive.getMsg())
			.userName(map.get(sid).getName())
			//data为扩展对象
			.data(Result.success(Result.success()))
			.time(LocalDateTime.now().toString())
			.build();

		sendToAllClient(JSON.toJSONString(webSocketData));

		//进入Redis缓存
		stringRedisTemplate.opsForList().rightPush("message:", JSON.toJSONString(TalkingMessage
									.builder()
									.message(webSocketData.getMsg())
									.userName(webSocketData.getUserName())
									.uuid(sid)
									.time(LocalDateTime.now())
									.timeString(Long.toString(System.currentTimeMillis()))
									.build()));

		//判断消息是否大于设定值
		Long size = stringRedisTemplate.opsForList().size("message:");
		//先判断消息队列
		if (size > SystemMsg.HUNDRED.total){
		    //将历史消息退出队列
		    stringRedisTemplate.opsForList().leftPop("message:");
		}
		flagNumber = flagNumber + 1;
		//判断消息大于100时存库一次
		if (flagNumber >= SystemMsg.HUNDRED.total){
		    //标记清空
		    flagNumber = 0;
		    //获取数据
		    List<String> range = stringRedisTemplate.opsForList().range("message:", 0, -1);
		    //创建线程执行
		    AddTalkingMessageList t1 = new AddTalkingMessageList(range);
		    t1.start();
		}

		break;
	    case 2:

		//TODO 状态码2为技能后期扩展需要处理。

		break;
	    default:
		//TODO 其他参数暂时为错误
		return;
	}

    }

    /**
     * 连接关闭调用的方法
     *
     * @param sid
     */
    @OnClose
    public void onClose(@PathParam("sid") String sid) {
	System.out.println("连接断开:" + sid);
	map.remove(sid);
    }

    /**
     * 群发
     *
     * @param message
     */
    public void sendToAllClient(String message) {
	Collection<WebSocketSession> sessions = map.values();
	for (WebSocketSession session : sessions) {
	    try {
		//服务器向客户端发送消息
		session.getSession().getBasicRemote().sendText(message);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }


    //获取列表
    public static Map getMap(){
	return map;
    }



    @Autowired
    public WebSocketServer(){
	//手动注入Bean对象
	this.userNameMapper = SpringBeanUtil.getBean(UserNameMapper.class);
    	this.stringRedisTemplate = SpringBeanUtil.getBean(StringRedisTemplate.class);
    }

}
