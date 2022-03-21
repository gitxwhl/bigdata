package com.canteen.service;

public interface StOrderManagementService {

    //订单列表
    Object getOrder(String paramJson);

    //通过订单号查询订单详情
    Object getOrderByNo(String paramJson);

    //获取下拉框数据
    Object dropDownBox();
}
