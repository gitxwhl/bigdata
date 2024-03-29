package com.mashibing.easypoi_boot.content;

import lombok.Getter;

/**
 * 固定的状态码
 * 固定的对象用枚举,枚举类要写在最前面
 */
public enum CommonStatusEnum {
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
