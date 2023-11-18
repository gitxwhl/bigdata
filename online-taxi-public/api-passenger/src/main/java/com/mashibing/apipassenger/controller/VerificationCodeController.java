package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.request.VerificationDTO;
import com.mashibing.apipassenger.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationCodeController {
    @Autowired
    private VerificationCodeService verificationCodeService;

    /**
     * 接收手机号调用验证码服务，获取验证码存入redis
     * @param verificationDTO
     * @return
     */
    @RequestMapping("/verification-code")
    public String verificationCode(@RequestBody VerificationDTO verificationDTO){
        return verificationCodeService.getVerificationCode(verificationDTO.getPassengerPhone());
    }



}
