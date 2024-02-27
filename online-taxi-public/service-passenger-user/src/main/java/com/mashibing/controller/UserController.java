package com.mashibing.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationDTO;
import com.mashibing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 根据手机号查询用户信息,如果有该用户不做任何操作，如果没有注册用户信息
     */
    @PostMapping("/user")
    public ResponseResult loginOrRegister(@RequestBody VerificationDTO verificationDTO){
        //获取手机号
        String passengerPhone = verificationDTO.getPassengerPhone();
        return userService.loginOrRegister(passengerPhone);
    }

    /**
     *根据手机号返回用户信息
     */
    @RequestMapping(method = RequestMethod.GET,value = "/user/{phone}")
    public ResponseResult getUserByphone(@PathVariable("phone") String phone){
        return userService.getUserbyphone(phone);
    }








}
