package com.example.anonymouschat.pojo.receive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebsocketReceive implements Serializable {

    private Integer code; //编码：1消息，2技能

    private String uuid;//用户名

    private String msg; //信息

}
