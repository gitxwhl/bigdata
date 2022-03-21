package com.canteen.service;

import com.canteen.entity.StLocaleat;

public interface StLocaleatService {

    //添加申请
    String addApplication(StLocaleat stLocaleat);

    //申请人查询保存的申请
    Object getSave(String paramJson);

    //修改保存的申请
    Object updateSave(StLocaleat stLocaleat);

    //删除保存的申请
    Object deleteSave(StLocaleat stLocaleat);

    //审批人查询已提交的申请
    Object getSubmit(String paramJson);

    //审批人进行审批
    Object approval(StLocaleat stLocaleat);

    //查询关联属性
    Object getRelevance();

    //查询待办
    Object getBacklog(String paramJson);
}
