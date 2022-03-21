package com.property.service;

import com.property.entity.Wyregister;
import com.property.entity.Wyworkorganization;

public interface WyworkorganizationService {

    //工作安排列表
    Object getJobPlacement(String paramJson);

    //新增工作安排
    Object addJobPlacement(String paramJson);

    //修改工作安排
    Object updateJobPlacement(Wyworkorganization wyworkorganization);

    //删除工作安排
    Object deleteJobPlacement(Wyworkorganization wyworkorganization);

    //工作提醒
    Object reminderWork();

    //登记植被种植情况
    Object addRegisterZz(String paramJson);

    //登记植被修剪情况
    Object addRegisterXj(String paramJson);

    //空间情况
    Object getSpaceInfo(String paramJson);

    //获取下拉框数据
    Object dropDownBox();
}
