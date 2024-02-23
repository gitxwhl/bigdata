package com.mashibing.apipassenger.controller;


import com.mashibing.apipassenger.service.VerificationCodeService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class VerificationCodeController {
    @Autowired
    private VerificationCodeService verificationCodeService;

    /**
     * 接收手机号调用验证码服务，获取验证码存入redis
     * @param verificationDTO
     * @return
     */
    @GetMapping("/verification-code")
    public ResponseResult verificationCode(@RequestBody VerificationDTO verificationDTO){
        return verificationCodeService.getVerificationCode(verificationDTO.getPassengerPhone());
    }
    /**
     * 校验验证
     */
    @PostMapping("/verification-code-check")
    public ResponseResult verificationCodeCheck(@RequestBody VerificationDTO verificationDTO){
    return verificationCodeService.verificationCodeCheck(verificationDTO.getPassengerPhone(),verificationDTO.getVerificationCode());

    }



}
