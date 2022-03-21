package com.property.service;

import com.property.utils.PageBean;

public interface WyirrigationmanagementService {

    //新增计划
    Object getNewlyIncreasedPlan(String param);
    //修改计划
    Object getamendPlan(String param);
    //返回灌溉方式
    Object getIrrigationMethods( );
    //计划列表
    PageBean WyirrigationmanagementList(String param);
    //计划详情接口
    Object getPlanDetails(String param);
    //计划评价 评估打分接口
    Object getAssessRedact(String param);
    //计划及检查情况图
    Object getIrrigateExamine(String param);
    //上传附件
    Object getuploading(String param);
}
