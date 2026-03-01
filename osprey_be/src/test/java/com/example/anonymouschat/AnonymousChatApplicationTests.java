package com.example.anonymouschat;

import com.alibaba.fastjson.JSON;
import com.example.anonymouschat.mapper.TalkingMessageMapper;
import com.example.anonymouschat.pojo.receive.WebsocketReceive;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import toolgood.words.StringSearch;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class AnonymousChatApplicationTests {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    TalkingMessageMapper talkingMessageMapper;


    @Test
    void contextLoads() {
        WebsocketReceive websocketReceive1 = WebsocketReceive
                .builder()
                .code(1)
                .uuid("0e25c344-cb5a-4b91-97c2-cf824710953d")
                .msg("你好我是法轮功")
                .build();
        System.out.println(JSON.toJSON(websocketReceive1));

        WebsocketReceive websocketReceive2 = WebsocketReceive
                .builder()
                .code(1)
                .uuid("e04ca2e0-f6c3-4f7c-8ffc-dbffa654103a")
                .msg("你好我是2号消息")
                .build();
        System.out.println(JSON.toJSON(websocketReceive2));
    }

    @Test
    void test(){
        System.out.println(System.currentTimeMillis());
        Long time = System.currentTimeMillis();
        Date date = new Date(time);
        System.out.println(date);
        LocalDateTime now = LocalDateTime.now();
        System.out.println(LocalDateTime.now());
        for (int i = 1 ; i <= 10; i ++){
            System.out.println(System.currentTimeMillis());
        }
    }

    @Test
    void test01(){
        String test = "我有一颗大土豆，刚出锅的夜场.票d女模特";
        List<String> list = new ArrayList<>();
        list.add("夜场女模特1");
        list.add("发票");
        list.add("模特");
        StringSearch stringSearch = new StringSearch();
        stringSearch.SetKeywords(list);
        boolean b = stringSearch.ContainsAny(test);
        System.out.println("b=" + b);
        return;
    }


}
