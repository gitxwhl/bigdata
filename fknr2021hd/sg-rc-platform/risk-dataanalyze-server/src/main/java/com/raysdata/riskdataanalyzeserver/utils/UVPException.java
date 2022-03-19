/*
 * 文 件 名：UVPException.java
 * 系统名称：电网统一视频监控平台
 * Copyright@2003-2019 State Grid Corporation of China, All Rights Reserved
 * 版本信息：V1.0
 * 版   权：NARI
 */

package com.raysdata.riskdataanalyzeserver.utils;

import java.util.List;

/**
 * 概述：
 * 功能：
 * 作者：Hans
 * 创建时间：2019-06-03 09:11
 */
public class UVPException extends RuntimeException {

    /**
     * 具体的错误描述
     */
    private final String errorDetail;
    private final ErrorCode errorCode;
    private final List<String> paramList;

    public UVPException() {
        this.errorCode = null;
        this.errorDetail = null;
        this.paramList = null;
    }

    public UVPException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorDetail = null;
        this.paramList = null;
    }

    public UVPException(String errorDetail) {
        this.errorCode = null;
        this.errorDetail = errorDetail;
        this.paramList = null;
    }

    public UVPException(ErrorCode errorCode, String errorDetail) {
        this.errorCode = errorCode;
        this.errorDetail = errorDetail;
        this.paramList = null;
    }

    public UVPException(ErrorCode errorCode, List<String> paramList) {
        this.errorCode = errorCode;
        this.errorDetail = null;
        this.paramList = paramList;
    }

    public UVPException(ErrorCode errorCode, String errorDetail, List<String> paramList) {
        this.errorCode = errorCode;
        this.errorDetail = errorDetail;
        this.paramList = paramList;
    }



    public List<String> getParamList() {
        return paramList;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
