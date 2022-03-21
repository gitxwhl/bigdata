package com.officeServices.service;

public interface DepartmentService {

    //部门导航栏
    Object getDepartmentList();

    //获取下级ids
    String getLowerIds(String id);
}
