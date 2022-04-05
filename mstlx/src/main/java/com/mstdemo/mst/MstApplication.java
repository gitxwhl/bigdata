package com.mstdemo.mst;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


//@ServletComponentScan(basePackages = "com.mstdemo.mst.config.*")
@SpringBootApplication
@MapperScan(value = "com.mstdemo.mst.mapper")
public class MstApplication {

    public static void main(String[] args) {
        SpringApplication.run(MstApplication.class, args);
    }

}
