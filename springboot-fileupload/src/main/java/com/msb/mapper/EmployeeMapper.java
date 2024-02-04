package com.msb.mapper;

import com.msb.pojo.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper {
    //插入员信息
    @Insert("INSERT INTO employee (empno,`name`,sex,age,job,deptmentid) VALUES (#{empno},#{name},#{sex},#{age},#{job},#{deptmentId})")
    int insert(Employee employee);

}
