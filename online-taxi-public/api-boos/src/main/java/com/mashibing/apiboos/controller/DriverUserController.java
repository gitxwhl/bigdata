package com.mashibing.apiboos.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverUserController {
    @RequestMapping("/test")
    public String test(){
        return "api-boos";
    }
}
