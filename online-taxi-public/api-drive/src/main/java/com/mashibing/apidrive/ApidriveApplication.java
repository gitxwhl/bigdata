package com.mashibing.apidrive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ApidriveApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApidriveApplication.class);
    }
    @RequestMapping("/api-drive")
    public String test(){
        return "api-drive";
    }

}
