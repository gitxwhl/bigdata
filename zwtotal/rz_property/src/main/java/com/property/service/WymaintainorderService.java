package com.property.service;

import com.property.utils.PageBean;

public interface WymaintainorderService {

    //获取工单信息
    PageBean getMaintainorder(String param);

    //获取工单状态
    Object getWorkState();

    //获取工单详情信息
    Object getMaintainorderDetails(String param);

    //修改派单信息
    void updateSingle(String param);

    //查询超期订单
    PageBean getBeyondOrder(String param);

    //重新生成超期订单告警
    void reOrderAlarm(String param);

    //取消超期订单告警
    void updateOrderAlarm(String param);

}
