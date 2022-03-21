package com.property.service;

import com.property.entity.Trepaircheck;
import com.property.entity.Ufrepair;

import java.text.ParseException;

public interface UfrepairService {

    //获取部门导航栏
    Object getWycorporateList();

    //提交报修申请
    Object addUfrepair(Ufrepair ufrepair);

    //报修人查询报修记录
    Object selectRecord(String paramJson);

    //管理员维修管理
    Object management(String paramJson);

    //管理员派单
    Object sendOrders(Ufrepair ufrepair);

    //完成维修
    Object finishWx(Ufrepair ufrepair) throws ParseException;

    //报修评价
    Object repairEvaluate(Ufrepair ufrepair);

    //页面数据展示
    Object getData();

    //获取下拉框数据
    Object dropDownBox();

    //通过id查询设备信息
    Object getEquipmentById(String paramJson);

}
