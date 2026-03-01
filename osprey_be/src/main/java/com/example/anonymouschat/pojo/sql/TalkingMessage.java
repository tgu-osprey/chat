package com.example.anonymouschat.pojo.sql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TalkingMessage implements Serializable {

    //信息内容
    private String message;

    //昵称
    private String userName;

    //uuid
    private String uuid;

    //时间
    private LocalDateTime time;

    //时间戳
    private String timeString;

}
