package com.mashibing.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @RequestMapping("/getTest")
    public String getTest(){
        return "service-passenger-user";
    }
}
