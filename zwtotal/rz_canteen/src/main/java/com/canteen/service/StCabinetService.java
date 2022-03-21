package com.canteen.service;

import com.canteen.entity.StCabinet;

import java.util.List;

public interface StCabinetService {

    //餐柜管理列表
    Object getCabinet(String paramJson);

    //查询订单详情
    Object getOrderInfo(String paramJson);

    //餐柜上货
    Object addOrder(String paramJson);

    //更换订单
    Object updateOrder(String paramJson);

    //清空餐柜
    Object emptyCabinet();

    //获取下拉框数据
    Object dropDownBox();
}
