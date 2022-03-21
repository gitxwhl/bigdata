package com.canteen.service;

import com.canteen.utils.PageBean;

import java.util.List;
import java.util.Map;

public interface StOperationrestaurantService {
    //获取运维餐厅级别菜单
    List getStOperationList(String deFlag);

    //获取策略列表
    PageBean getStrategyList(String paramJson);

    //删除策略
    String deleteStrategy(String paramJson);

    //查询可关联设备
    //Map<String,List> getEquipment();

    //修改策略
    String updateStrategy(String paramJson);

    //新增策略
    Object insertStrategy(String paramJson);

    //获取字典表定义
    Map dictionaries();
}
