package com.property.service;

import com.property.utils.PageBean;

public interface WyequipmentinspectionService {
    //获取设备巡检信息
    PageBean selectEquipmentInspection(String param);

    //添加巡检计划信息
    Object insertEquipmentInspection(String param);

    //添加巡检记录信息
    Object updateEquipmentInspection(String param);

    //获取线路名称，涉及设备，巡检结果
    Object getInspectionAndResults();
}
