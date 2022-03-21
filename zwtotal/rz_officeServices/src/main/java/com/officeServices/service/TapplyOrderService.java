package com.officeServices.service;

import com.officeServices.entity.ApplyOrder;
import com.officeServices.utils.ResultMessage;

import java.util.Map;

public interface TapplyOrderService {

    //提交办公服务用品申请
    Object submitApplyOrder(Map<String,Object> map);

    //审批人查询审批列表
    Object getApprovalList(Map<String,Object> map);

    //审批人审批
    Object approveApply(Map<String,Object> map);

    //审批通过列表查询
    //Object getPassList(Map<String,Object> map);

    //发放物品
    Object grantGoods(Map<String, Object> map);

    //下拉框
    Object dropDownBox(Map<String,Object> map);

    //通过id查询物品详情
    Object getGoodInfo(Map<String,Object> map);

    //获取当前部门已审批的金额和最高金额
    ResultMessage getRemainDepMoney(Map<String, Object> map);
}
