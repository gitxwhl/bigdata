package com.raysdata.riskdataanalyzeserver.service;

import com.raysdata.riskdataanalyzeserver.utils.PageBean;

import java.util.List;
import java.util.Map;

public interface RiskWorkStaffInfoService {

    /**
     * 方法注释
     * @param paramJson
     * @return
     */
    PageBean<Map<String, Object>> getRiskWorkerInfosList(String paramJson);

    /**
     * 方法注释
     * @param paramJson
     * @return
     */
    PageBean<Map<String, Object>> getRiskWorkertoProjectList(String paramJson);

    /**
     * 方法注释
     * @param paramJson
     * @return
     */
    PageBean<Map<String, Object>> getRiskWorkerLinkageInfosList(String paramJson);

    /**
     * 方法注释
     * @param paramJson
     * @return
     */
    PageBean<Map<String, Object>> getRiskWorkerUnitChangeList(String paramJson);

    /**
     * 方法注释
     * @param paramJson
     * @return
     */
    PageBean<Map<String, Object>> getRiskWorkerViolationList(String paramJson);


    /**
     * 方法注释
     * @param paramJson
     * @return
     */
    PageBean<Map<String, Object>> getRiskWorkerSafeInfoList(String paramJson);

    /**
     * 方法注释
     * @param paramJson
     * @return
     */
    PageBean<Map<String, Object>> getRiskWorkerSafeLearnList(String paramJson);

    /**
     * 方法注释
     * @param paramJson
     * @return
     */
    PageBean<Map<String, Object>> getRiskWorkerSafetyTestList(String paramJson);

    /**
     * 方法注释
     * @param paramJson
     * @return
     */
    PageBean<Map<String, Object>> getRiskWorkerViolationPointsList(String paramJson);


    /**
     * 人员信息统计
     * @return
     */
    Map<String,Object> getStaffCount();


    /**
     * 人员安全资信统计
     * @return
     */
    Map<String,Object> getSafeCount();


    /**
     * 准入情况列表
     * @param paramJson
     * @return
     */
    PageBean<Map<String, Object>> getRiskWorkerAccessList(String paramJson);


    /**
     * 人员详情查询
     * @param paramJson
     * @return
     */
    List<Map<String,Object>> getWorkerInfo(String paramJson);
}


