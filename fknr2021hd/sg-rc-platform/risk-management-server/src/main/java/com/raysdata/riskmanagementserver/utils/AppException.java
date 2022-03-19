///*
// * 文 件 名：AppException.java
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
// * 创建时间：2019-06-03 09:11
// */
//public class AppException extends RuntimeException {
//
//    /**
//     * 具体的错误描述
//     */
//    private final String errorDetail;
//    private final ErrorCode errorCode;
//
//    public AppException() {
//        this.errorCode = null;
//        this.errorDetail = null;
//    }
//
//    public AppException(ErrorCode errorCode) {
//        this.errorCode = errorCode;
//        this.errorDetail = null;
//    }
//
//    public AppException(ErrorCode errorCode, String errorDetail) {
//        this.errorDetail = errorDetail;
//        this.errorCode = errorCode;
//    }
//
//    public String getErrorDetail() {
//        return errorDetail;
//    }
//
//    public ErrorCode getErrorCode() {
//        return errorCode;
//    }
//
//}
