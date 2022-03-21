package com.property.mapper;

import com.property.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DepartmentMapper {

    /**
     * 菜单树查询
     */
    @Select("SELECT  * FROM department")
    List<Department> findStDepartment();



}
