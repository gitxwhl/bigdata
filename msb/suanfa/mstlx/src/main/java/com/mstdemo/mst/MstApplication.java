package com.mstdemo.mst;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan(value = "com.mstdemo.mst.mapper")
public class MstApplication {

    public static void main(String[] args) {
        SpringApplication.run(MstApplication.class, args);
    }

}
