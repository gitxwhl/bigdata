package com.canteen.service;

import com.canteen.entity.StMealmanagement;

public interface StMealmanagementService {

    //直接取餐列表
    Object directList(String paramJson);

    //餐柜取餐列表
    Object plateList(String paramJson);

    //更改取餐方式
    Object updateMode(StMealmanagement stMealmanagement);

    //下拉框数据
    Object dropDownBox();
}
