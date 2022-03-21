package com.property.service;


import com.property.utils.PageBean;

public interface InspectionplanService {
    //新增巡检计划
    Object insertWyInspectionplan(String param);
    //巡检计划列表
    PageBean wyInspectionplanList(String param);
    //修改巡检计划
    Object updateWyInspectionplan(String param);
    //删除巡检计划
    Object deleteInspectionplan(String param);
}
