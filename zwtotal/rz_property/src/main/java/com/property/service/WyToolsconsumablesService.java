package com.property.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WyToolsconsumablesService {

    //列表
    Object getToolConsumption(String param);

    //新增材料及人员信息
    Object insertMaterialsStaff(String param);

    //修改信息
    Object updateMaterialsStaff(String param);

    //删除信息
    Object deleteMaterialsStaf(String param);

    //文件导出
    Object fileImport(HttpServletRequest request, HttpServletResponse response) throws Exception;

    //文件导入
    Object fileExample(HttpServletRequest request, HttpServletResponse response) throws Exception;

    //数据详情
    Object insertDetails(String param);
}
