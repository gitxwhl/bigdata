package com.property.bootstrap;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@EnableAutoConfiguration
@MapperScan(value = "com.property.mapper")
@EnableTransactionManagement
@ComponentScan({"com.property.controller", "com.property.service"})
public class PropertyApplication {
    public static void main(String[] args) {
        SpringApplication.run(PropertyApplication.class, args);
    }
}
