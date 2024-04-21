package com.mashibing.apidrive.service;

import com.mashibing.internalcommon.dto.ResponseResult;

public interface VerificationcodeService {

    //根据手机号发送验证码
    ResponseResult verificationcode(String driverPhone);

}
