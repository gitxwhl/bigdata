package com.mashibing.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @RequestMapping("/service-map-test")
    public String find(){
        return "service-map";
    }
}
