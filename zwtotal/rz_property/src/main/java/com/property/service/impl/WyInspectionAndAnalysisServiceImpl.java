package com.property.service.impl;

import com.property.mapper.WyInspectionAndAnalysisMapper;
import com.property.service.WyInspectionAndAnalysisService;
import com.property.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WyInspectionAndAnalysisServiceImpl  implements WyInspectionAndAnalysisService {
    @Autowired
    WyInspectionAndAnalysisMapper wyInspectionAndAnalysisMapper;

    /*
    保洁计划检查查询
     */
    @Override
    public PageBean selectInspectionAndAnalysis(String param) {
        return null;
    }

    /*
    保洁情况统计
     */
    @Override
    public Map CleaningStatistics(String param) {
        return null;
    }

    /*
    计划完成情况统计
     */
    @Override
    public Map PlanCompletionStatistics(String param) {
        return null;
    }
}
