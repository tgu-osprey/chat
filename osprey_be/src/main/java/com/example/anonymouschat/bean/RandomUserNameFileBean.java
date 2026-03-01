package com.example.anonymouschat.bean;

import com.alibaba.fastjson.JSON;
import com.example.anonymouschat.config.FileConfig;
import com.example.anonymouschat.constant.SystemMsg;
import com.example.anonymouschat.pojo.file.RandomUserName;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@Data
@Slf4j
@DependsOn("fileConfig")
public class RandomUserNameFileBean {

    @Autowired
    private FileConfig fileConfig;

    @Autowired
    private ResourceLoader resourceLoader;

    //随机昵称类
    private RandomUserName randomUserName;


    @PostConstruct
    //获取本地文件并将字符串
    private void init(){

        //优先配置文件中路径
        String userNameFile = fileConfig.getUserNameFile();
        if (Strings.isNotBlank(userNameFile)) {
            try {
                loadFile(userNameFile);
                return;
            } catch (IOException e) {
                log.warn("配置文件中用户名读取失败，尝试进行jar包中载入");
            }
        }

        //其次保底jar包中文件
        userNameFile = SystemMsg.RANDOM_USER_NAME.msg;
        if (Strings.isNotBlank(userNameFile)) {
            try {
                Resource resource = resourceLoader.getResource(userNameFile);
                byte[] content = FileCopyUtils.copyToByteArray(resource.getInputStream());
                String json = new String(content, StandardCharsets.UTF_8);
                this.randomUserName = JSON.parseObject(json, RandomUserName.class);
            } catch (IOException e) {
                throw new RuntimeException("jar包中文件加载失败，必要功能缺失，启动失败");
            }
        }
    }


    private void loadFile(String filePath) throws IOException {
        String res = Files.readString(Path.of(filePath));
        //解析数据
        RandomUserName randomUserName = JSON.parseObject(res,RandomUserName.class);
        //封装进对象
        this.randomUserName = randomUserName;
    }

}
