package com.mashibing.apidrive.controller;
import com.mashibing.apidrive.service.VerificationcodeService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
@Slf4j
public class VerificationcodeController {
    @Autowired
    private VerificationcodeService verificationcodeService;

    /**
     * 根据手机号发送验证码
     */
    @GetMapping("/verificationcode")
    public ResponseResult verificationcode(@RequestBody VerificationDTO verificationDTO){
        log.info(verificationDTO.getDriverPhone());
        return verificationcodeService.verificationcode(verificationDTO.getDriverPhone());
    }




}
