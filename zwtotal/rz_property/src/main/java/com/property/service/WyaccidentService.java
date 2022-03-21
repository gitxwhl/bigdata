package com.property.service;

public interface WyaccidentService {

    //添加事故记录
    Object addAccident(String paramJson);

    //查询事故记录
    Object getAccident(String paramJson);

    //获取下拉框数据
    Object dropDownBox();
}
