package com.zyz.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zyz.demo.mapper")
public class TprPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(TprPlatformApplication.class, args);
    }

}
