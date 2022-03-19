package com.raysdata.riskdataanalyzeserver.service;

import com.alibaba.fastjson.JSONObject;
import com.raysdata.riskdataanalyzeserver.utils.PageBean;

import java.util.List;
import java.util.Map;

public interface RiskAccidentAnalysisService {

    /**
     * 设备事件表数据展示
     * @param paramJson
     * @return
     */
    PageBean<Map<String,Object>> getEquipAccidentList(String paramJson);


    /**
     * 人身事件表数据展示
     * @param paramJson
     * @return
     */
    PageBean<Map<String,Object>> getPersonalAccidentList(String paramJson);


    /**
     * 电网事件表数据展示
     * @param paramJson
     * @return
     */
    PageBean<Map<String,Object>> getPowerGridAccidentList(String paramJson);


    /**
     * 网络信息事件表数据展示
     * @param paramJson
     * @return
     */
    PageBean<Map<String,Object>> getInformAccidentList(String paramJson);


    /**
     * 安全事故分类统计
     * @param paramJson
     * @return
     */
    Map<String,Integer> getAccidentStatistics(String paramJson);


    /**
     * 按类型同比统计
     * @param paramJson
     * @return
     */
    Map<String, Map> getAccidentCountByMonth(String paramJson);


    /**
     * 按类型环比统计
     * @return
     */
    Map<String, Map> getAccidentCount();

    /**
     * 趋势预测分析
     * @return
     */
    Map<String, Object> getTendencyAnalyze();

    /**
     * 按等级统计
     * @param paramJson
     * @return
     */
    List<Map<String, Object>> getAccidentGrade(String paramJson);

    /**
     * 按单位统计
     * @return
     */
    List<Map<String, Object>> getAccidentGridcrop();

    /**
     * 按电压等级统计
     * @return
     */
    List<Map<String, Object>> getAccidentVoltageLevel();

    /**
     * 按事故原因TOP10统计
     * @param paramJson
     * @return
     */
    List<Map<String, Object>> getAccidentDesc(String paramJson);


    /**
     * 获取字段表
     * @return
     */
    Map<String, List<JSONObject>> getSrpRiskSysTab();

    /*
     *事件数量统计
     * paramJson:{"timeType":""}
     * */
    Map<String, Integer> getAccidentStatistics1(String paramJson);








}
