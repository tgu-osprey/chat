package com.example.anonymouschat.pojo.websocket;

import com.example.anonymouschat.constant.ErrorMsg;
import com.example.anonymouschat.constant.StatusCode;
import com.example.anonymouschat.constant.SystemMsg;
import com.example.anonymouschat.websocket.WebSocketServer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebSocketExtendMessage implements Serializable {

    //扩展状态码
    private Short code;

    //扩展信息
    private String Data;

    //在线人数
    private Integer onlineCount;

    //心跳报文
    public static WebSocketExtendMessage heart(){

	//获取聊天室人数
	Map map = WebSocketServer.getMap();

	return WebSocketExtendMessage.builder()
		.code(StatusCode.DATA_心跳报文状态码.code)
		.Data(SystemMsg.TIME_TO_CONTINUE.msg)
		.onlineCount(map.size())
		.build();
    }


    //顶号报文
    public static WebSocketExtendMessage remote(){
	return WebSocketExtendMessage.builder()
		.code(StatusCode.DATA_顶号报文状态码.code)
		.Data(ErrorMsg.您的昵称已在其他设备进入聊天室.msg)
		.build();
    }

    //敏感词报文
    public static WebSocketExtendMessage sensitiveWord(){
	return WebSocketExtendMessage.builder()
		.code(StatusCode.DATA_敏感信息报文状态码.code)
		.Data(ErrorMsg.消息中包含非法字符.msg)
		.build();
    }
}
