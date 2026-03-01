package com.example.anonymouschat.pojo.result;

import com.example.anonymouschat.pojo.sql.TalkingMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UuidRes implements Serializable {

    //uuid
    private String uuid;

    //聊天信息列表
    private List<TalkingMessage> talkingMessages;

}
