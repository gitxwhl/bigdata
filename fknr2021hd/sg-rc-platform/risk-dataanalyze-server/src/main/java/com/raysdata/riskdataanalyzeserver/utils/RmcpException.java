package com.raysdata.riskdataanalyzeserver.utils;

import java.util.List;

/**
 * * Copyright ©2003- 2020   State Grid Corporation of China, All Rights Reserved.
 * Copyright: NARI
 *
 * @Project: 安全生产风险管控平台
 * @Version：V1.0
 * @Package: com.rmcp.base.service.entity
 * @Author: zhouyanbin
 * @Create: 4/26/20 10:54 PM
 * @Description: 风控异常
 * @History: modify
 **/
public class RmcpException extends RuntimeException {

    /**
     * 具体的错误描述
     */
    private final String errorDetail;
    private final ErrorCodeEnums ErrorCodeEnums;
    private final List<String> paramList;

    public RmcpException() {
        this.ErrorCodeEnums = null;
        this.errorDetail = null;
        this.paramList = null;
    }

    public RmcpException(ErrorCodeEnums ErrorCodeEnums) {
        this.ErrorCodeEnums = ErrorCodeEnums;
        this.errorDetail = null;
        this.paramList = null;
    }

    public RmcpException(ErrorCodeEnums ErrorCodeEnums, String errorDetail) {
        this.ErrorCodeEnums = ErrorCodeEnums;
        this.errorDetail = errorDetail;
        this.paramList = null;
    }

    public RmcpException(ErrorCodeEnums ErrorCodeEnums, List<String> paramList) {
        this.ErrorCodeEnums = ErrorCodeEnums;
        this.errorDetail = null;
        this.paramList = paramList;
    }

    public RmcpException(ErrorCodeEnums ErrorCodeEnums, String errorDetail, List<String> paramList) {
        this.ErrorCodeEnums = ErrorCodeEnums;
        this.errorDetail = errorDetail;
        this.paramList = paramList;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public ErrorCodeEnums getErrorCodeEnums() {
        return ErrorCodeEnums;
    }

    public List<String> getParamList() {
        return paramList;
    }
}
