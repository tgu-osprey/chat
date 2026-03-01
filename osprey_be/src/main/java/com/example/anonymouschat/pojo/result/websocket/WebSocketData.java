package com.example.anonymouschat.pojo.result.websocket;

import com.example.anonymouschat.constant.ErrorMsg;
import com.example.anonymouschat.constant.SystemMsg;
import com.example.anonymouschat.pojo.websocket.WebSocketExtendMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebSocketData<T> implements Serializable {

    //信息
    private String msg;
    //昵称
    private String userName;
    //时间
    private String time;
    //预留对象
    private T data;


    //心跳报文
    public static WebSocketData heart(){
        WebSocketData heart = WebSocketData.builder()
                .data(WebSocketExtendMessage.heart())
                .msg(SystemMsg.HEART_MESSAGE.msg)
                .userName(SystemMsg.SERVICE.msg)
                .time(DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()))
                .build();
        return heart;
    }

    //顶号报文
    public static WebSocketData remote(){
        WebSocketData remote = WebSocketData.builder()
                .data(WebSocketExtendMessage.remote())
                .msg(ErrorMsg.您的昵称已在其他设备进入聊天室.msg)
                .userName(SystemMsg.SERVICE.msg)
                .time(DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()))
                .build();
        return remote;
    }

    //敏感词信息报文
    public static WebSocketData sensitiveWord(){
        WebSocketData sensitiveWord = WebSocketData.builder()
                .data(WebSocketExtendMessage.sensitiveWord())
                .msg(ErrorMsg.消息中包含非法字符.msg)
                .userName(SystemMsg.SERVICE.msg)
                .time(DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()))
                .build();
        return sensitiveWord;
    }

}
