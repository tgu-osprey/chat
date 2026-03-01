package com.example.anonymouschat.controller.userController;

import com.example.anonymouschat.pojo.request.MessageRequest;
import com.example.anonymouschat.pojo.request.UserUuidRequest;
import com.example.anonymouschat.pojo.request.ApplyForNameRequest;
import com.example.anonymouschat.pojo.result.http.Result;
import com.example.anonymouschat.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    //申请用户名
    @PostMapping("/apply")
    public Result applyForName(@RequestBody ApplyForNameRequest applyForNameRequest){
	Result res = userService.applyForName(applyForNameRequest);
        return res;
    }

    //废弃用户名
    @PostMapping("/abandon")
    public Result abandonUserName(@RequestBody UserUuidRequest userUuidRequest){
        Result res = userService.abandonUserName(userUuidRequest);
        return res;
    }

    //获取昵称
    @PostMapping("/login")
    public Result login(@RequestBody UserUuidRequest userUuidRequest){
        Result res = userService.login(userUuidRequest);
        return res;
    }

    //获取历史消息
    @PostMapping("/historymessage")
    public Result getHistoryMessage(@RequestBody MessageRequest messageRequest){
        Result res = userService.getHistoryMessage(messageRequest);
        return res;
    }

    //获取随机昵称
    @GetMapping("/randomusername")
    public Result randomUserName(){
        Result res = userService.randomUserName();
        return res;
    }
}
