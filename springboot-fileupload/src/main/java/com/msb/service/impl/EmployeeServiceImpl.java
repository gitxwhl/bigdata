package com.msb.service.impl;
import com.msb.mapper.EmployeeMapper;
import com.msb.pojo.Employee;
import com.msb.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public int inserts(List<Employee> emps) {
        int result=0;
        for (Employee employee:emps){
             result = employeeMapper.insert(employee);
        }
         return result;
    }
}
