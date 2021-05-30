package com.zyz.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.zyz.*"})

@MapperScan("com.zyz.demo.mapper")
public class TprPlatformApplication {

    public static String[] args;
    public static ConfigurableApplicationContext context;


    public static void main(String[] args) {
        TprPlatformApplication.args = args;
        TprPlatformApplication.context = SpringApplication.run(TprPlatformApplication.class, args);
    }
}
