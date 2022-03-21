package com.property.service;

import com.property.entity.Department;

import java.util.List;

public interface DepartmentService {
    /**
     * 部门菜单树查询
     */
    List<Department> findStDepartment();

}
