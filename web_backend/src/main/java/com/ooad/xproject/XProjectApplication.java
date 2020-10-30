package com.ooad.xproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ooad.xproject.mapper")
public class XProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(XProjectApplication.class, args);
    }
}
