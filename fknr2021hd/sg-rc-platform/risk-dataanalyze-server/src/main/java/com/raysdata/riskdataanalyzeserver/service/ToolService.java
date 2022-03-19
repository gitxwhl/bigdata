package com.raysdata.riskdataanalyzeserver.service;

import com.raysdata.riskdataanalyzeserver.utils.PageBean;

import java.util.List;
import java.util.Map;

public interface ToolService {

    /**
     * 数据统计
     * @return
     */
    Map<String,List> dataStatistics();


    /**
     * 实时预警
     * @param paramJson
     * @return
     */
    PageBean<Map<String,Object>> realWarning(String paramJson);


    /**
     * 供应商评价
     * @param paramJson
     * @return
     */
    Map<String,List> getGradeList(String paramJson);
}
