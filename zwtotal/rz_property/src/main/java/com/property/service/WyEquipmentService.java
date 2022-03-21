package com.property.service;

import com.property.entity.Wyequipment;
import com.property.utils.PageBean;

import java.util.Map;

public interface WyEquipmentService {

    //获取设备台账信息
    PageBean selectEquipment(String param);

    //添加设备台账信息
    void insertEquipment(Wyequipment stEquipment);

    //查询修改设备信息：存在安全风险方法隐藏
//    Map selectEquipmentById(String param);
    //修改设备
    void updateEquipment(Wyequipment stEquipment);

    //删除设备台账
    void deleteEquipment(String param);

    //生成二维码：存在安全风险方法隐藏
//    String getCode(String param);
    //历史维护记录查询 基本
    Object wyEquipmentList(String param);
    //历史维护记录查询 记录
    PageBean WySpacemanagementListDis(String param);
}
