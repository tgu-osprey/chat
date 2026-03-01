package com.example.anonymouschat.pojo.result;

import com.example.anonymouschat.pojo.request.MessageRequest;
import com.example.anonymouschat.pojo.sql.TalkingMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserNameRes implements Serializable {

    //昵称
    private String userName;

    //消息列表
    private List<TalkingMessage> talkingMessages;

}
