package com.property.service;

import com.property.entity.Wydutyrecord;

public interface WydutyrecordService {

    //添加值班记录
    Object addDutyRecord(Wydutyrecord wydutyrecord);

    //查询值班记录
    Object getDutyRecord(String paramJson);

    //获取下拉框数据
    Object dropDownBox();
}
