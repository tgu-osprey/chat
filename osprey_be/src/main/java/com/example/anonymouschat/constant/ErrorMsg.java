package com.example.anonymouschat.constant;

public enum ErrorMsg {

    该昵称已经存在且正在使用("该昵称已经存在且正在使用"),
    该昵称已经存在但正在弃用冷却期间("该昵称已经存在但正在弃用冷却期间"),
    未查询到该昵称("未查询到该昵称"),
    该昵称的状态已为被弃用("该昵称的状态已为被弃用"),
    用户名为空或与UUID不匹配("用户名为空或与UUID不匹配"),
    当前未传入UUID或未进入聊天室("当前未传入UUID或未进入聊天室"),
    您的昵称已在其他设备进入聊天室("您的昵称已在其他设备进入聊天室"),
    昵称中包含非法字符("昵称中包含非法字符"),
    消息中包含非法字符("消息中包含非法字符");

    public String msg;


    ErrorMsg(String msg){
        this.msg = msg;
    }


}
