package com.raysdata.riskmanagementserver.service;

import com.raysdata.riskmanagementserver.utils.PageBean;

import java.util.List;
import java.util.Map;

/*
 * 产业安全风险页面
 * */
/**
 * @author zyy
 */
public interface RiskIndusRiskWarnService {

    /**
     * 电网建设风险预警情况
     * industryType:产业类型
     * @param industryType
     * @return
     * */
    List<Map<String, Object>> getSrpriskindusriskwarnareacnt(String industryType);


    /**
     * 企业分布情况汇总
     * @return
     * */
    Map<String, Object> getSrpriskdkycompanycnt();


    /**
     * 人员构成汇总统计
     * @return
     * */
    Map<String, Object> getSrpriskdkycompanystaffcnt();


    /**
     * 产业安全风险预警
     * @return
     * */
    Map<String, Object> getRiskindusriskwarncnt();


    /**
     * 水库、大坝、水电站情况统计
     * @return
     * */
    Map<String, Object> getSrpriskstationinfo();


    /**
     * 抽水蓄能电站洞室施工建设情况汇总
     * @return
     * */
    Map<String, Object> getRiskhydropowerstationinfo();


    /**
     * 生物质料场情况汇总
     * @return
     * */
    Map<String, Object> getRiskbiologicalmaterinfo();


    /**
     * 产业风险业预警单分页查询
     * param :json参数
     * @param param
     * @return
     * */
    PageBean<Map<String, Object>> getSrpriskindusriskwarnlist(String param);

}
