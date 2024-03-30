package com.mashibing.driveuser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mashibing.driveuser.mapper")
public class ServiceDriveUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceDriveUserApplication.class);
    }
}
