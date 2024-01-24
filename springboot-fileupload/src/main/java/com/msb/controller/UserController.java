package com.msb.controller;

import com.msb.pojo.User;
import com.msb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/UserController.do")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findUser")
    private List<User> findUser(){
        return userService.findUser();
    }


}
