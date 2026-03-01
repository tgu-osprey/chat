package com.example.anonymouschat.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageRequest implements Serializable {

    //用户昵称
    private String userName;

    //uuid
    private String uuid;

    //时间
    private String timeString;
}
