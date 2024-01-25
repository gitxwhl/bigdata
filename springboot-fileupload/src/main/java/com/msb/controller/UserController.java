package com.msb.controller;

import com.msb.pojo.User;
import com.msb.service.UserService;
import com.msb.util.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/UserController.do")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findUser")
    private ResponseResult findUser(
            @RequestBody User user,
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize){
        return   ResponseResult.success(userService.findUser(user,pageNum,pageSize));
    }


}
