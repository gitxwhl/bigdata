package com.property.service;

import com.property.entity.Wyequipmentstrategy;
import com.property.utils.PageBean;

import java.util.Map;

public interface WyequipmentstrategyService {

    //查询维保策略
    PageBean getEquipmentStrategy(String param);

    //添加维保策略
    void insertEquipmentStrategy(String param);

    //查询修改维保策略
    Map getEquipmentStrategyById(String param);
    //修改维保策略
    void updateEquipmentStrategy(Wyequipmentstrategy wyequipmentstrategy);

    //删除维保策略
    void deleteEquipmentStrategy(String param);

    //获取策略分类
    Object getPolicy();
}
