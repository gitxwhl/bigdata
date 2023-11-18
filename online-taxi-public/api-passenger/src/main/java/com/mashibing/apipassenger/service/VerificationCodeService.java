package com.mashibing.apipassenger.service;

public interface VerificationCodeService {


    /**
     * 接收手机号调用验证码服务，获取验证码存入redis
     * @return
     */
    public String getVerificationCode(String code);






}
