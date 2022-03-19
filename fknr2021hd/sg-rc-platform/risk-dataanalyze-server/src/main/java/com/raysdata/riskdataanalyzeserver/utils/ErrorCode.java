/*
 * 文 件 名：ErrorCode.java
 * 系统名称：电网统一视频监控平台
 * Copyright@2003-2019 State Grid Corporation of China, All Rights Reserved
 * 版本信息：V1.0
 * 版   权：NARI
 */

package com.raysdata.riskdataanalyzeserver.utils;

public enum ErrorCode {

    DELETE_GROUP_APP_FAILURE_EXCEPTION("DELETE_GROUP_APP_FAILURE_EXCEPTION", "在分组中删除应用失败"),
    ADD_GROUP_APP_FAILURE_EXCEPTION("ADD_GROUP_APP_FAILURE_EXCEPTION", "在分组中添加应用失败"),
    APP_EXIST_EXCEPTION("APP_EXIST_EXCEPTION", "应用已经存在"),
    DELETE_GROUP_FAILURE_EXCEPTION("DELETE_GROUP_FAILURE_EXCEPTION", "删除自定义分组失败"),
    UPDATE_GROUP_FAILURE_EXCEPTION("UPDATE_GROUP_FAILURE_EXCEPTION", "更新自定义分组失败"),
    ADD_GROUP_FAILURE_EXCEPTION("ADD_GROUP_FAILURE_EXCEPTION", "新建自定义分组失败"),
    GROUP_NAME_EXIST_EXCEPTION("GROUP_NAME_EXIST_EXCEPTION", "分组名称已经存在"),
    SYSTEM_EXCEPTION("SYSTEM_EXCEPTION", "系统异常"),
    USER_NOT_LOGIN_EXCEPTION("USER_NOT_LOGIN_EXCEPTION", "用户未登录"),
    PARAM_VALIDATE_EXCEPTION("PARAM_VALIDATE_EXCEPTION", "参数校验异常");

    private String code;
    private String desc;

    ErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDescByValue(String value) {
        for (ErrorCode enums : ErrorCode.values()) {
            if (enums.getCode() == value) {
                return enums.getDesc();
            }
        }
        return "";
    }

}


