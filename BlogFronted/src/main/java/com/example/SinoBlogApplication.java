package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@MapperScan("com.example.mapper")
public class SinoBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(SinoBlogApplication.class, args);
    }
}
