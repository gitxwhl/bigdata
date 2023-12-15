package com.mashibing.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationDTO;
import com.mashibing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 根据手机号查询用户信息
     */
    @PostMapping("/user")
    public ResponseResult loginOrRegister(@RequestBody VerificationDTO verificationDTO){
        //获取手机号
        String passengerPhone = verificationDTO.getPassengerPhone();
        return userService.loginOrRegister(passengerPhone);
    }


}
