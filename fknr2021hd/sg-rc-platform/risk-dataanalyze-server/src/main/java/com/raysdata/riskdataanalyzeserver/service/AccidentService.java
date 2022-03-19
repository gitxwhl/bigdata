package com.raysdata.riskdataanalyzeserver.service;

import com.raysdata.riskdataanalyzeserver.utils.PageBean;

import java.util.List;
import java.util.Map;

public interface AccidentService {

    /**
     * 外包单位风险画像列表
     * @param paramJson
     * @return
     */
    PageBean<Map<String,Object>> getRiskPortrait(String paramJson);


    /**
     * 单位基本情况
     * @param paramJson
     * @return
     */
    Map<String,List> basicInformation(String paramJson);

    /**
     * 单位安全资信
     * @param paramJson
     * @return
     */
    Map<String,List> securityInformation(String paramJson);


    /**
     * 全网信息联动
     * @param paramJson
     * @return
     */
    Map<String,List> networkInformation(String paramJson);

    /**
     * 安全能力分析
     * @param paramJson
     * @return
     */
    List safetyAnalysis(String paramJson);
}
