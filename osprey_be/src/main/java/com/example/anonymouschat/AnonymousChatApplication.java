package com.example.anonymouschat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.URL;


@SpringBootApplication
@EnableScheduling
@Slf4j
public class AnonymousChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnonymousChatApplication.class, args);
	    log.info("服务已开启");
    }

}
