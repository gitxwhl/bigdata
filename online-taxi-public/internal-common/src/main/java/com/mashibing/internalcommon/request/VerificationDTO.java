package com.mashibing.internalcommon.request;

import lombok.Data;

@Data
public class VerificationDTO {
    //手机号
    private String passengerPhone;
    //验证码
    private String verificationCode;
}
