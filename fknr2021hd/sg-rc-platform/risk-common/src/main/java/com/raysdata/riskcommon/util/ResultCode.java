package com.raysdata.riskcommon.util;

/**
 * * Copyright ©2003- 2020   State Grid Corporation of China, All Rights Reserved.
 * Copyright: NARI
 *
 * @Project: 安全生产风险管控平台
 * @Version：V1.0
 * @Package: com.nariit.rmcp.common
 * @Author: MaoXiaoDong
 * @Create: 2020-05-18 14:35
 * @Description: this file is used for 返回code常量
 * @History:
 **/
public interface ResultCode {

    /**
     * 成功
     */
    int SUCCESS = 0;

    /**
     * 系统异常
     */
    int SYSTEM_ERROR = 1;

    /**
     * 参数校验失败
     */
    int PARAM_VALIDATE_ERROR = 2;

    /**
     * 用户未登录
     */
    int NOT_LOGIN_ERROR = 3;

    /**
     * 数据库操作失败
     */
    int DB_OPERATE_ERROR = 4;

    /**
     * Excel操作失败
     */
    int EXCEL_OPERATE_ERROR = 5;

    /**
     * Redis操作失败
     */
    int REDIS_OPERATE_ERROR = 6;

    /**
     * 业务处理失败
     */
    int BUSINESS_HANDLE_ERROR = 7;

    /**
     * PI6000调用失败
     */
    int PI6000_INVOKE_ERROR = 8;


}
