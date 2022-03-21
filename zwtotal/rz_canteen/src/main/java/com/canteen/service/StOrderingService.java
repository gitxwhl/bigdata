package com.canteen.service;

import com.canteen.entity.StCabinet;

public interface StOrderingService {

    //线上订餐列表查询
    Object getOrderList(String paramJson);

    //预订
    Object reservation(String paramJson);

    //预订菜品详情
    Object getOrderInfo(String paramJson);

    //取消预订
    Object cancelOrder(String paramJson);

    //查询菜品信息
    Object getDishes(String paramJson);

    //下拉框数据
    Object dropDownBox();

    //-----------------------------------------------打包服务----------------------------------------------------------
    //打包服务列表查询
    Object getPackageService(String paramJson);

    //打包上柜
    Object packaging(StCabinet stCabinet);

    //下拉框数据
    Object getDropDownBox();

    //--------------------------------------------异地餐食配送------------------------------------------------------
    //异地配送列表查询
    Object distributionList(String paramJson);

}
