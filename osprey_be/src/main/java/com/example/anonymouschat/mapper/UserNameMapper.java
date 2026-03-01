package com.example.anonymouschat.mapper;

import com.example.anonymouschat.pojo.sql.UserName;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserNameMapper {

    //获取昵称列表
    List<UserName> getUserNameByRandomName(String userName);

    //新增昵称信息
    void addUserName(UserName userName);

    //更新昵称状态
    void updateUserNameStatus(UserName userName);

    //每天两点清除超过五天弃用的名称
    @Delete("delete from user_name where status = 0 and abandon_time < NOW() - interval 5 day;")
    void clearnUserNameTable();

    //通过UUID获取识别码
    UserName getUserNameByUuid(String uuid);
}
