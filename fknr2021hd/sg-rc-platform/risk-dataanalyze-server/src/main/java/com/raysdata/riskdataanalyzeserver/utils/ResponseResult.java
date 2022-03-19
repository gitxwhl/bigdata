package com.raysdata.riskdataanalyzeserver.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;


public class ResponseResult implements Serializable {

    /**
     * 服务端处理是否成功
     */
    private boolean successful;

    /**
     * 返回值
     */
    private Object resultValue;

    /**
     * 错误提示
     */
    private String resultHint;

    /**
     * 错误自定义页面路径
     */
    private String errorPage;

    /**
     * 不成功时的类型info、warn或error
     */
    private String type;

    /**
     * 日志信息
     */
    @JsonIgnore
    private String opLog;

    public ResponseResult() {

    }

    public ResponseResult(Boolean successful, Object resultValue, String resultHint, String errorPage, String type) {
        this.successful = successful;
        this.resultValue = resultValue;
        this.resultHint = resultHint;
        this.errorPage = errorPage;
        this.type = type;
    }

    public ResponseResult(Boolean successful, Object resultValue, String resultHint, String errorPage, String type, String opLog) {
        this.successful = successful;
        this.resultValue = resultValue;
        this.resultHint = resultHint;
        this.errorPage = errorPage;
        this.type = type;
        this.opLog = opLog;
    }

    public static ResponseResult successResult(Object resultValue,String opLog) {
        return new ResponseResult(true, resultValue, "200", null, null,opLog);
    }

    public static ResponseResult successResult(Object resultValue) {
        return new ResponseResult(true, resultValue, "调用成功", null, null);
    }

    public static ResponseResult failedResult(String resultHint) {
        return new ResponseResult(false, null, resultHint, "", "");
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public Object getResultValue() {
        return resultValue;
    }

    public void setResultValue(Object resultValue) {
        this.resultValue = resultValue;
    }

    public String getResultHint() {
        return resultHint;
    }

    public void setResultHint(String resultHint) {
        this.resultHint = resultHint;
    }

    public String getErrorPage() {
        return errorPage;
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOpLog(){
        return this.opLog;
    }

    public void setOpLog(String opLog) {
        this.opLog = opLog;
    }
}
