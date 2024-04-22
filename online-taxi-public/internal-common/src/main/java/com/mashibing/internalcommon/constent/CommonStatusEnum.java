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
    //1300-1399
    PRICE_RULE_EMPTY(1300,"计价规则不存在"),
    //1400-1499
    DISTRICT_ERROR(1400,"获取地图信息失败"),
//    1500-1599  司机车辆
    DRIVER_CAR_BIND_NOT_EXISTS(1500,"司机车辆绑定关系不存在"),
    DRIVER_BIND_NOT_EXISTS(1501,"司机不存在"),
    DRIVER_CAR_BIND_EXISTS(1502,"司机车辆绑定关系已存在，请勿重复绑定"),
    DRIVER_BIND_EXISTS(1503,"司机已经绑定，请勿重复绑定"),
    CAR_BIND_EXISTS(1504,"车辆已经绑定，请勿重复绑定"),
    DRIVER_NOT_EXISTS(1505,"司机不存在"),
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
