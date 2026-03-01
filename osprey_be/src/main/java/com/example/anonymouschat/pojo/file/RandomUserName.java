package com.example.anonymouschat.pojo.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RandomUserName {

    //形容词
    private List<String> adjective;

    //连接词
    private List<String> conjunction;

    //名词
    private List<String> noun;
}
