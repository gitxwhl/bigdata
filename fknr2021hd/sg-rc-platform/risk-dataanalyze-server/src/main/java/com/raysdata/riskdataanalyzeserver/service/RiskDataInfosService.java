package com.raysdata.riskdataanalyzeserver.service;

import com.raysdata.riskdataanalyzeserver.utils.PageBean;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface RiskDataInfosService {

    /**
     * 数据治理表查询
     * @param paramJson
     * @return
     */
    PageBean<Map<String,Object>> getDataInfoManage(String paramJson);


    /**
     * 数据清洗表查询
     * @param paramJson
     * @return
     */
    PageBean<Map<String,Object>> getDataInfoPurge(String paramJson);

    /**
     * 数据加载
     * @param paramJson
     * @return
     */
    PageBean<Map<String,Object>> getDataInfoLoadList(String paramJson);

    /**
     * 数据转换
     * @param paramJson
     * @return
     */
    PageBean<Map<String,Object>> getDataInfoConversionList(HttpServletRequest request, String paramJson);
}
