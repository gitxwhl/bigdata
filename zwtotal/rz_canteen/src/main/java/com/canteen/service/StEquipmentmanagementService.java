package com.canteen.service;

import com.canteen.utils.PageBean;

import java.util.Map;

public interface StEquipmentmanagementService {
    //获取设备列表
    PageBean getEquipmentList(String paramJson);

    //删除设备
    String deleteEquipment(String paramJson);

    //新增设备
    String insertEquipment(String paramJson);

    //修改设备
    String updateEquipment(String paramJson);

    //查询关联属性(运维餐厅，设备状态)
    Map getRelevance();
}
