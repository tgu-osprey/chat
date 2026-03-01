package com.example.anonymouschat.service.user;

import com.example.anonymouschat.pojo.request.MessageRequest;
import com.example.anonymouschat.pojo.request.UserUuidRequest;
import com.example.anonymouschat.pojo.request.ApplyForNameRequest;
import com.example.anonymouschat.pojo.result.http.Result;

public interface UserService {
    Result applyForName(ApplyForNameRequest applyForNameRequest);

    Result abandonUserName(UserUuidRequest userUuidRequest);

    Result login(UserUuidRequest userUuidRequest);

    Result getHistoryMessage(MessageRequest messageRequest);

    Result randomUserName();
}
