package com.example.anonymouschat.service.user.Impl;

import com.alibaba.fastjson.JSON;
import com.example.anonymouschat.bean.RandomUserNameFileBean;
import com.example.anonymouschat.bean.SensitiveWordDetection;
import com.example.anonymouschat.constant.ErrorMsg;
import com.example.anonymouschat.mapper.TalkingMessageMapper;
import com.example.anonymouschat.mapper.UserNameMapper;
import com.example.anonymouschat.pojo.file.RandomUserName;
import com.example.anonymouschat.pojo.request.MessageRequest;
import com.example.anonymouschat.pojo.request.UserUuidRequest;
import com.example.anonymouschat.pojo.request.ApplyForNameRequest;
import com.example.anonymouschat.pojo.result.RandomUserNameRes;
import com.example.anonymouschat.pojo.result.UserNameRes;
import com.example.anonymouschat.pojo.result.UuidRes;
import com.example.anonymouschat.pojo.sql.TalkingMessage;
import com.example.anonymouschat.pojo.sql.UserName;
import com.example.anonymouschat.pojo.websocket.WebSocketSession;
import com.example.anonymouschat.pojo.result.http.Result;
import com.example.anonymouschat.service.user.UserService;
import com.example.anonymouschat.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserNameMapper userNameMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    TalkingMessageMapper talkingMessageMapper;

    @Autowired
    RandomUserNameFileBean randomUserNameFileBean;


    //申请用户名
    @Override
    public Result applyForName(ApplyForNameRequest applyForNameRequest) {

	//敏感词检测

	if (SensitiveWordDetection.politicallySensitiveWordDetection(applyForNameRequest.getName())) {
	    return Result.error(ErrorMsg.昵称中包含非法字符.msg);
	}

	//1、查询数据库中是否存在该名字
	List<UserName> userNameByRandomName = userNameMapper.getUserNameByRandomName(applyForNameRequest.getName());
	//判断列表是否为空
	if (userNameByRandomName.size() == 0){
	    //为空，创建相应名称
	    UserName userName = UserName.builder()
		    .randomName(applyForNameRequest.getName())
		    .status((short)1)
		    .uuid(UUID.randomUUID().toString())
		    .build();
	    userNameMapper.addUserName(userName);

	    //返回uuid
	    //返回Redis中聊天数据
	    List<String> stringList = stringRedisTemplate.opsForList().range("message:", 0, -1);

	    //创建返回消息变量集合
	    List<TalkingMessage> talkingMessages = new ArrayList<>();
	    for (String s : stringList) {
		//进行反序列化操作
		talkingMessages.add(JSON.parseObject(s,TalkingMessage.class));
	    }

	    //构建返回对象
	    UuidRes res = UuidRes.builder()
		    .uuid(userName.getUuid())
		    .talkingMessages(talkingMessages)
		    .build();

	    return Result.success(res);
	}else{
	    //返回错误信息，该昵称已被使用
	    if (userNameByRandomName.get(0).getStatus() == 1){
		return Result.error(ErrorMsg.该昵称已经存在且正在使用.msg);
	    }else{
		return Result.error(ErrorMsg.该昵称已经存在但正在弃用冷却期间.msg);
	    }
	}
    }

    //弃用用户名
    @Override
    public Result abandonUserName(UserUuidRequest userUuidRequest) {

	//查询是否存在该识别码
	UserName name = userNameMapper.getUserNameByUuid(userUuidRequest.getUuid());

	if (name == null){
	    //如果为空，则返回错误信息
	    return Result.error(ErrorMsg.未查询到该昵称.msg);
	}else {
	    if (name.getStatus() == 0){
		//如果该昵称已被废弃，则返回已被废弃
		return Result.error(ErrorMsg.该昵称的状态已为被弃用.msg);
	    }
	}
	//查询到了，进行修改
	name.setStatus((short)0);
	name.setAbandonTime(LocalDateTime.now());
	//修改使用的昵称状态
	userNameMapper.updateUserNameStatus(name);
	return Result.success();
    }

    //用户登录/获取用户名
    @Override
    public Result login(UserUuidRequest userUuidRequest) {

	//查询数据库
	UserName userNameByUuid = userNameMapper.getUserNameByUuid(userUuidRequest.getUuid());

	if (userNameByUuid == null){
	    //判断为空，返回查询不到
	    return Result.error(ErrorMsg.未查询到该昵称.msg);
	}

	if (userNameByUuid.getStatus() == 0){
	    //判断是否弃用,返回弃用
	    return Result.error(ErrorMsg.该昵称的状态已为被弃用.msg);
	}
	//返回Redis中聊天数据
	List<String> stringList = stringRedisTemplate.opsForList().range("message:", 0, -1);
	//创建返回消息变量集合
	List<TalkingMessage> talkingMessages = new ArrayList<>();
	for (String s : stringList) {
	    //进行反序列化操作
	    talkingMessages.add(JSON.parseObject(s,TalkingMessage.class));
	}
	//返回昵称
	UserNameRes res = UserNameRes.builder()
			.userName(userNameByUuid.getRandomName())
			.talkingMessages(talkingMessages)
			.build();
	return Result.success(res);
    }

    //查历史（非缓存中）消息接口
    @Override
    public Result getHistoryMessage(MessageRequest messageRequest) {

	//此段代码需要严格遵守
	// 先登录->获取用户名与uuid
	// 随即进入聊天室->建立ws连接
	// 最后调取查询信息接口

	//获取Ws连接信息
	Map map = WebSocketServer.getMap();
	WebSocketSession webSocketSession = (WebSocketSession) map.get(messageRequest.getUuid());

	if (webSocketSession == null){
	    return Result.error(ErrorMsg.当前未传入UUID或未进入聊天室.msg);
	}
	//判断uuid是否与姓名匹配
	if (!messageRequest.getUserName().equals(webSocketSession.getName())){
	    return Result.error(ErrorMsg.用户名为空或与UUID不匹配.msg);
	}

	//查询历史消息
	List<TalkingMessage> list = talkingMessageMapper.getMessageByTimeString(messageRequest.getTimeString());
	System.out.println(list.size());
	return Result.success(list);

    }

    //获取随机名称
    @Override
    public Result randomUserName() {

	//声明随机数类
	Random random = new Random();

	//获取随机昵称列表
	RandomUserName randomUserName = randomUserNameFileBean.getRandomUserName();

	//随机昵称
	String userName = randomUserName.getAdjective().get(random.nextInt(randomUserName.getAdjective().size()))
		+ randomUserName.getConjunction().get(random.nextInt(randomUserName.getConjunction().size()))
		+ randomUserName.getNoun().get(random.nextInt(randomUserName.getNoun().size()));
	//设置返回值
	return Result.success( new RandomUserNameRes(userName));
    }


}
