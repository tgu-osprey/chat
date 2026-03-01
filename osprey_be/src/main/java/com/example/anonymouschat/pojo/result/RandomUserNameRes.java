package com.example.anonymouschat.pojo.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RandomUserNameRes implements Serializable {

    //随机昵称
    private String userName;

}
