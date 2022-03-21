package com.property.service.impl;

import com.property.entity.Department;
import com.property.mapper.DepartmentMapper;
import com.property.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;
    /**
     * 部门菜单树查询
     */
    @Override
    public List<Department> findStDepartment() {
        return departmentMapper.findStDepartment();
    }
















}
