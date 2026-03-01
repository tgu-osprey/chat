package com.example.anonymouschat.constant;


public enum SystemMsg {

    //服务器名
    SERVICE("@service"),

    //hello心跳报文
    HEART_MESSAGE("hello"),

    //缓存中消息条数
    HUNDRED(100),

    //心跳报文Data内置信息
    TIME_TO_CONTINUE("time to continue"),

    //超时聊天消息日志信息
    TALKING_MESSAGE_FILE_PATH("/home/osprey/messageLog/"),

    //随机昵称文件
    RANDOM_USER_NAME("classpath:RandomUserName.json");

    //敏感词库文件路径
//    SENSITIVE_PATH("G:\\敏感词\\sensitive-stop-words-master\\politics.txt");
//    SENSITIVE_PATH(AnonymousChatApplication.class.getResource("/politics.txt").getPath());

    public String msg;

    public Integer total;
    SystemMsg(String msg){
	    this.msg = msg;
    }
    SystemMsg(Integer total){
        this.total = total;
    }
}
