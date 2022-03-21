package com.property.service;


import com.property.entity.Wyinspectionplan;
import com.property.utils.PageBean;

public interface WypersonService {

    //-----------------------------------------------排班计划-----------------------------------------------------
    //查询班次明细
    Object getFrequency(String paramJson);

    //添加班次明细
    Object addFrequency(String paramJson);

    //修改班次明细
    Object updateFrequency(String paramJson);

    //-----------------------------------------------巡检计划-----------------------------------------------------
    //巡检计划列表
    Object inspectionList(String paramJson);

    //新增巡检计划
    Object addInspection(String paramJson);

    //修改巡检计划
    Object updateInspection(String paramJson);

    //删除巡检计划
    Object deleteInspection(String paramJson);

    //下拉框
    Object dropDownBox();

    //-----------------------------------------------岗位资料-----------------------------------------------------
    //新增岗位资料
    Object insertWyPerson(String param);

    //秩维计划  岗位列表
    PageBean wypersonList(String param);
}
