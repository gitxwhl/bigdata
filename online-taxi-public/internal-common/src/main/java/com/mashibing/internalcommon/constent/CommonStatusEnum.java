package com.mashibing.internalcommon.constent;

import lombok.Data;
import lombok.Getter;

/**
 * 固定的状态码
 * 固定的对象用枚举,枚举类要写在最前面
 */
public enum CommonStatusEnum {
    //1000--1099
    VERIFICATION_CODE_ERREO(1009,"验证码错误"),
    //1000--1099
    TOOKEN_ERROR(1199,"tooken错误"),
    //用户提示：1200-1299
    USER_NOT_EXISTS(1200,"当前用户不存在"),
    SUCESS(1,"sucess"),
    FAIL(0,"fail");
    @Getter
    private int code;
    @Getter
    private String value;

    CommonStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
