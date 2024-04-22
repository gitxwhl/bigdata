package com.mashibing.internalcommon.request;

import lombok.Data;

@Data
public class VerificationDTO {
    //乘客手机号
    private String passengerPhone;
    //验证码
    private String verificationCode;
    //司机手机号
    private String driverPhone;
}
