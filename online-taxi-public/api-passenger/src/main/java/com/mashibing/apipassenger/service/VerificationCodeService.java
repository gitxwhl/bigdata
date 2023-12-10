package com.mashibing.apipassenger.service;

import com.mashibing.internalcommon.dto.ResponseResult;

public interface VerificationCodeService {


    /**
     * 接收手机号调用验证码服务，获取验证码存入redis
     * @return
     */
     ResponseResult getVerificationCode(String code);






}
