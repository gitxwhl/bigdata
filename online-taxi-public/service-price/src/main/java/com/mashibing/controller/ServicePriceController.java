package com.mashibing.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServicePriceController {
    @RequestMapping("/find")
    public String find(){
        return "find";
    }




}
