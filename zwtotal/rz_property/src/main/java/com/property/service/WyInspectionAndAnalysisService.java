package com.property.service;

import com.property.utils.PageBean;

import java.util.Map;

public interface WyInspectionAndAnalysisService {

    //保洁计划检查查询
    PageBean selectInspectionAndAnalysis(String param);

    //保洁情况统计
    Map CleaningStatistics(String param);

    //计划完成情况统计
    Map PlanCompletionStatistics(String param);
}
