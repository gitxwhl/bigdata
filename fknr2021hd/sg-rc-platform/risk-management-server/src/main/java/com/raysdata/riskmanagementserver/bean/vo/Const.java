package com.raysdata.riskmanagementserver.bean.vo;

import java.util.Arrays;
import java.util.List;

/**
 * * Copyright ©2003- 2020   State Grid Corporation of China, All Rights Reserved.
 * Copyright: NARI
 *
 * @Project: 安全生产风险管控平台
 * @Version：V1.0
 * @Package: com.nariit.rmcp.consts
 * @Author: zhuzeming
 * @Create: 2020/4/30 10:53
 * @Description: this file is used for 普通常量定义
 * @History: modify
 * * 1900-01-01 12:00:00 modified by xxx
 **/
public interface Const {

    // JSON返回
    String RESPONSE_JSON_PRODUCES = "application/json;charset=UTF-8";

    //mybatis参数
    String MYBATIS_PARAMS_LIST = "list";
    String MYBATIS_PARAMS_PARAM = "param1";
    int REDISSON_CLIENT_TIME = 120;

    String STRING_ONE = "1";
    String MINUS_ONE = "-1";
    String STRING_ZERO = "0";

    //导出excel的版本类型
     String EXCEL2003L = ".xls"; //2003以下版本的excel
     String EXCEL2007U = ".xlsx";//2007以上版本的excel

     int NUM_ZERO   = 0;
     int NUM_ONE    = 1;
     int NUM_TWO    = 2;
     int NUM_THREE  = 3;
     int NUM_SEVEN  = 7;
    /**
     * 考勤类型
     */
    String SIGN_IN = "签到";
    String SIGN_OUT = "签退";

    /**
     * 考勤状态
     */
    String TIMEOUT = "早退";
    String BELATE = "迟到";
    String NORMAL = "正常";

    /**
     * 选项前缀
     */
    String[] OPTION_PRE={"A. ","B. ","C. ","D. ","E. ","F. ","G. ","H. ","I. ","J. ","K. "};
    String CORRECT="正确";
    String ERROR="错误";

    int SUBJECTMAXSIZE=30000;

    /** start 检查计划状态 **/
    String CHECKSTATUS_UNSTART="2003001";//未开始
    String CHECKSTATUS_CHECKING="2003002";//检查中
    String CHECKSTATUS_CHANGING="2003003";//变更待审核
    String CHECKSTATUS_END="2003004";//已结束
    /** end 检查计划状态**/

    /** start 审批状态 **/
    String AUDIT_STATUS_SUCCESS="1";//审核成功
    String AUDIT_STATUS_REFUSED="0";//审核拒绝
    /** end 审批状态**/

    /** start 班组人员变动信息情况 **/
    String BZ_CHANGE_STATUS_SUCCESS="提交成功";
    String BZ_CHANGE_STATUS_FAIL="班组人员变动信息不能为空！";
    /** end 班组人员变动信息情况**/
    
    /*负面清单和黑名单默认纳入条件*/
    String NEGATIVE_CONTENT="达到负面清单纳入阈值";
    String BLACK_CONTENT="达到黑名单纳入阈值";

    /**
     * 字典转换类型
     */
    String DICT_DATATYPE_LIST   ="list";
    String DICT_DATATYPE_FIELD  ="field";
    String DICT_DATATYPE_CLASS  ="class";

    /**
     * 反射调取类的get，set
     */
    String METHOD_GET="get";
    String METHOD_SET="set";
    String METHOD_LIST_SIZE="size";

    String NULL = "null";

    String CURRENT_TASK="C";//BPM当前历史任务
    String HISTORY_TASK="H";//BPM历史任务

    List<String> UNITLIST= Arrays.asList("DSDW","XDW","SDW","DW");//单位集合

}
