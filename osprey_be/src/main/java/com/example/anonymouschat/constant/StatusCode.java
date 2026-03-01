package com.example.anonymouschat.constant;

public enum StatusCode {

    DATA_心跳报文状态码(200),
    DATA_顶号报文状态码(600),
    DATA_敏感信息报文状态码(700);


    public Short code;


    //构造方法
    StatusCode(Integer code){
	this.code = code.shortValue();
    }



}
