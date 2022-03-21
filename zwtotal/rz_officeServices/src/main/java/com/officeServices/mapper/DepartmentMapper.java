package com.officeServices.mapper;

import com.officeServices.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DepartmentMapper {

    //查询部门信息
    List<Department> getDepartment();

    //通过id查询父级
    String getParentId(@Param("id")String id);

    //查询下级部门id
    List<String> getLowerId(@Param("id")String id);

    //查询部门等级
    String getLevel(@Param("id")String id);
}
