package com.example.anonymouschat.constant;


import java.util.concurrent.TimeUnit;


//Redis枚举类型
public enum RedisEnum {

    //有效期为一秒
    ONE_SECONDS(1,TimeUnit.SECONDS),

    //有效期为五秒
    FIVE_SECONDS(5,TimeUnit.SECONDS),

    //有效期为二十分钟
    TWENTY_MINUTES(20,TimeUnit.MINUTES);

    //单位
    private TimeUnit unit;
    //有效时间
    private int time;


    //构造方法
    RedisEnum(int time , TimeUnit unit){
        this.time = time;
        this.unit = unit;
    }

    //get方法
    public TimeUnit getUnit() {
        return unit;
    }

    public int getTime() {
        return time;
    }
}
