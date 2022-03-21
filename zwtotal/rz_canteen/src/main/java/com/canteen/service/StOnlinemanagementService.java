package com.canteen.service;

import com.canteen.entity.StOnlinemanagement;

public interface StOnlinemanagementService {

    //线上菜品管理列表
    Object getOnlineList(String paramJson);

    //是否支持线上预订
    Object updateReserve(StOnlinemanagement stOnlinemanagement);

    //是否支持配送
    Object updateDistribution(StOnlinemanagement stOnlinemanagement);

    //获取下拉框数据
    Object dropDownBox();

    //营养成分分析
    Object getNutritional(String param);
}
