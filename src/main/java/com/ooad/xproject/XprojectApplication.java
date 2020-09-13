package com.ooad.xproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class XprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(XprojectApplication.class, args);
    }

}
