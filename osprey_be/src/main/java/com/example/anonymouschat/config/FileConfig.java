package com.example.anonymouschat.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
public class FileConfig {

    @Value("${file.user-name}")
    private String userNameFile;

    @Value("${file.sensitive-file}")
    private String sensitiveFile;

}
