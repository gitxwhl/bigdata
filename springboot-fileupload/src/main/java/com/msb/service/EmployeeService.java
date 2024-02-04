package com.msb.service;

import com.msb.pojo.Employee;

import java.util.List;

public interface EmployeeService {

    //插入员工信息
    int inserts(List<Employee> ems);
}
