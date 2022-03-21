package com.property.service;

import com.property.utils.PageBean;

public interface WySpacemanagementListService  {

    //供应商管理列表
    PageBean WySpacemanagementList(String param);

    //植被列表
    PageBean WyvegetationtList(String param);

    //新增
    Object getSupplierManagement(String param);


}
