package com.bwie.hm0422;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.bwie.hm0422.dao")
@EnableScheduling
public class Hm0422Application {

    public static void main(String[] args) {
        SpringApplication.run(Hm0422Application.class, args);
    }

}
