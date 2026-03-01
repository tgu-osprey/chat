package com.example.anonymouschat.pojo.websocket;

import jakarta.websocket.Session;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class WebSocketSession {
    //Ws对话对象
    private Session session;
    //名称
    private String name;
}
