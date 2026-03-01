package com.example.anonymouschat.Task;

import com.alibaba.fastjson.JSON;
import com.example.anonymouschat.file.SaveFile;
import com.example.anonymouschat.mapper.TalkingMessageMapper;
import com.example.anonymouschat.mapper.UserNameMapper;
import com.example.anonymouschat.pojo.sql.TalkingMessage;
import com.example.anonymouschat.pojo.result.websocket.WebSocketData;
import com.example.anonymouschat.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class Task {

    @Autowired
    private WebSocketServer webSocketServer;

    @Autowired
    private UserNameMapper userNameMapper;

    @Autowired
    private TalkingMessageMapper talkingMessageMapper;
    /**
     * 通过WebSocket每隔5秒向客户端发送消息
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void sendMessageToClient() {

        //返回心跳信息
        webSocketServer.sendToAllClient(JSON.toJSONString(WebSocketData.heart()));

    }

    //每天凌晨两点执行
    @Scheduled(cron = "0 0 2 * * ?")
    public void clearnUserNameTable(){
        //定时任务开始
        long begin = System.currentTimeMillis();

        //清除超出冷却期昵称
        userNameMapper.clearnUserNameTable();

        //查询消息记录
        List<TalkingMessage> list = talkingMessageMapper.getTimeoutMessage();

        //如果查询到数据执行
        if (list.size() != 0){
            //将信息存至文本文件
            //创建文件名
            String fileName = LocalDateTime.now() + ".txt";
            //存库
            SaveFile.save(fileName,JSON.toJSONString(list));

            //从数据库中删除
            talkingMessageMapper.batchDelete();
        }

        //输出任务花费时间
        long end =System.currentTimeMillis();
        log.info("定时任务结束：共耗时：[" + (end-begin) / 1000 + "]秒");
    }
}
