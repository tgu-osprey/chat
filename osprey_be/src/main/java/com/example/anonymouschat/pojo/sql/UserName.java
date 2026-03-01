package com.example.anonymouschat.pojo.sql;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserName implements Serializable {

    //用户名
    private String randomName;

    //状态值（1启用0弃用）
    private Short status;

    //uuid(唯一识别码)
    private String uuid;

    //废弃时间
    private LocalDateTime abandonTime;

}
