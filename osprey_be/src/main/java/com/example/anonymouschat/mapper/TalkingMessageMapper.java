package com.example.anonymouschat.mapper;

import com.example.anonymouschat.pojo.sql.TalkingMessage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.DependsOn;

import java.util.List;

@Mapper
public interface TalkingMessageMapper {
    //新增信息
    void addTalkingMessageList(List<TalkingMessage> list);

    //查询超时时间
    @Select("select * from talking_message where time < NOW() - interval 5 day;")
    List<TalkingMessage> getTimeoutMessage();

    @Delete("delete from talking_message where time < NOW() - interval 5 day")
    void batchDelete();

    List<TalkingMessage>getTalkingMessageList();

    List<TalkingMessage> getMessageByTimeString(String timeString);
}
