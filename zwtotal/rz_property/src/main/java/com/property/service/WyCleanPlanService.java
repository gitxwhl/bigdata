package com.property.service;

import com.property.utils.PageBean;

import java.util.Map;

public interface WyCleanPlanService {
    //保洁计划查询
    PageBean selectCleanPlan(String param);

    //删除保洁计划
    void deleteCleanPlan(String param);

    //修改-根据id返回相关信息
    Map updateOfContent(String param);

    //修改保洁计划
    void updateCleanPlan(String param);

    //新建保洁计划
    void insertCleanPlan(String param);

    //检查计划查询
    PageBean selectInspectionPlan(String param);

    //检查计划删除
    void deleteInspectionPlan(String param);

    //修改--根据id查询信息
    Map selectInspectionPlanById(String param);

    //修改检查计划
    void updateInspectionPlan(String param);

    //新建检查计划
    void insertInspectionPlan(String param);
}
