package com.example.anonymouschat.thread;

import com.alibaba.fastjson.JSON;
import com.example.anonymouschat.mapper.TalkingMessageMapper;
import com.example.anonymouschat.pojo.sql.TalkingMessage;
import com.example.anonymouschat.beanUtil.SpringBeanUtil;

import java.util.ArrayList;
import java.util.List;

public class AddTalkingMessageList extends Thread{


    TalkingMessageMapper talkingMessageMapper;

    private List<String> range;

    @Override
    public void run() {
	List<TalkingMessage> list  = new ArrayList<>();
        for (String s : range) {
            list.add(JSON.parseObject(s,TalkingMessage.class));
        }
        talkingMessageMapper.addTalkingMessageList(list);
    }

    public AddTalkingMessageList(List<String> range){
        this.range = range;
        this.talkingMessageMapper = SpringBeanUtil.getBean(TalkingMessageMapper.class);
    }

}
