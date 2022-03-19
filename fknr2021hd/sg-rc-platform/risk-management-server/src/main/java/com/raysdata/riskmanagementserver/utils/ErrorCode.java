///*
// * 文 件 名：ErrorCode.java
// * 系统名称：电网统一视频监控平台
// * Copyright@2003-2019 State Grid Corporation of China, All Rights Reserved
// * 版本信息：V1.0
// * 版   权：NARI
// */
//
//package com.raysdata.riskmanagementserver.utils;
//
///**
// * 概述：
// * 功能：
// * 作者：Hans
// * 创建时间：2019-05-31 16:58
// */
//public enum ErrorCode {
//
//    SYSTEM_EXCEPTION("SYSTEM_EXCEPTION", "系统异常"),
//    NAME_LENGTH_VALIDATE_EXCEPTION("NAME_LENGTH_VALIDATE_EXCEPTION", "名称长度校验失败"),
//    USER_NOT_LOGIN_EXCEPTION("USER_NOT_LOGIN_EXCEPTION", "用户未登录"),
//    PARAM_VALIDATE_EXCEPTION("PARAM_VALIDATE_EXCEPTION", "参数校验异常"),
//    WORK_PLAN_NOT_EXIST_EXCEPTION("WORK_PLAN_NOT_EXIST_EXCEPTION", "作业计划不存在"),
//    WORK_MANAGER_NOT_SIGNIN_EXCEPTION("WORK_MANAGER_NOT_SIGNIN_EXCEPTION", "现场负责人未签到"),
//    APPEAL_MORE_THAN_TWICE_EXCEPTION("APPEAL_MORE_THAN_TWICE_EXCEPTION", "已申诉超过两次"),
//    APPEAL_ALREADY_COMMIT_EXCEPTION("APPEAL_ALREADY_COMMIT_EXCEPTION", "申诉已提交"),
//    USER_NOT_EXIST_EXCEPTION("USER_NOT_EXIST_EXCEPTION", "用户不存在"),
//    DEPT_OBTAIN_FAIL_EXCEPTION("DEPT_OBTAIN_FAIL_EXCEPTION", "部门获取失败"),
//    OBTAIN_MULTIPLY_DEPT_EXCEPTION("OBTAIN_MULTIPLY_DEPT_EXCEPTION", "该用户关联多个部门"),
//    EXTERNAL_DEPT_OBTAIN_FAIL_EXCEPTION("EXTERNAL_DEPT_OBTAIN_FAIL_EXCEPTION", "外部人员部门信息获取失败"),
//    PECCANCY_ALREADY_EXIST_EXCEPTION("PECCANCY_ALREADY_EXIST_EXCEPTION", "该条违章已创建成功"),
//    EXAM_QUESTIONS_NOT_ENOUGH_EXCEPTION("EXAM_QUESTIONS_NOT_ENOUGH_EXCEPTION", "考试题不足"),
//    CREATE_TEST_TYPE_FAIL_EXCEPTION("CREATE_TEST_TYPE_FAIL_EXCEPTION", "创建试题失败"),
//    EXAM_PAPER_ALREADY_SUBMITTED_EXCEPTION("EXAM_PAPER_ALREADY_SUBMITTED_EXCEPTION", "考卷已提交"),
//    FILE_UPLOAD_FAILED_EXCEPTION("FILE_UPLOAD_FAILED_EXCEPTION", "文件上传失败"),
//	PERMISSION_TOKEN_EXPIRED("PERMISSION_TOKEN_EXPIRED", "token已过期"),
//	PERMISSION_TOKEN_INVALID("PERMISSION_TOKEN_INVALID", "无效token"),
//	PERMISSION_SIGNATURE_ERROR("PERMISSION_TOKEN_INVALID", "签名失败"),
//	USER_NOT_LOGGED_IN("USER_NOT_LOGGED_IN", "用户未登录，请先登录");
//
//    private String code;
//    private String desc;
//
//    ErrorCode(String code, String desc) {
//        this.code = code;
//        this.desc = desc;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public String getDesc() {
//        return desc;
//    }
//
//    public static String getDescByValue(String value) {
//        for (ErrorCode enums : ErrorCode.values()) {
//            if (enums.getCode() == value) {
//                return enums.getDesc();
//            }
//        }
//        return "";
//    }
//
//}
//
//
