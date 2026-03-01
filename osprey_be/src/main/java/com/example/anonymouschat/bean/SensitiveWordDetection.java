package com.example.anonymouschat.bean;


import com.example.anonymouschat.config.FileConfig;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import toolgood.words.StringSearch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class SensitiveWordDetection {

    @Autowired
    private FileConfig fileConfig;

    private static StringSearch stringSearch = new StringSearch();

    private static List<String> sensitiveWord;

    public static boolean politicallySensitiveWordDetection(String target) {

        //设置工具类中的字符串
        stringSearch.SetKeywords(sensitiveWord);

        boolean b = stringSearch.ContainsAny(target);
        //判断结果返回
        return stringSearch.ContainsAny(target);
    }

    @PostConstruct
    public void init() {

        //以下为敏感词
        //优先配置文件中
        //jar包中
        String sensitiveWordPath = fileConfig.getSensitiveFile();

        if (Strings.isNotBlank(sensitiveWordPath)) {
            try {
                loadFile(sensitiveWordPath);
            } catch (IOException e) {
                log.warn("配置文件中敏感词加载失败，默认不提供敏感词服务");
            }
        }else {
            this.sensitiveWord = new ArrayList<>();
        }
    }

    private void loadFile (String filePath) throws IOException {

        //以下为政治敏感词

        //创建列表
        List<String> sensitiveWord = new ArrayList<>();

        //读取文件获取敏感词数据
        String res = Files.readString(Path.of(filePath));
        res = res.replaceAll("\\n", "");
        int flag = 0;
        //解析字符串数据
        while (true) {
            //获取分割符','
            int i = res.indexOf(',', flag + 1);
            if (i == -1) {
                break;
            }
            //加入列表
            sensitiveWord.add(res.substring(flag, i));
            flag = i + 1;
        }
        //政治敏感词至此结束
        this.sensitiveWord = sensitiveWord;
    }
}
