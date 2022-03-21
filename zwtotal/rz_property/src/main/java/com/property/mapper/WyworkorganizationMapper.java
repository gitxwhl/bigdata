package com.property.mapper;

import com.property.entity.Wyregister;
import com.property.entity.Wyworkorganization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface WyworkorganizationMapper {

    //工作安排数量
    Integer getJobPlacementCnt(@Param("name")String name);
    //工作安排列表
    List getJobPlacement(@Param("name")String name,@Param("index")Integer index,@Param("pageSize")Integer pageSize);

    //新增工作安排
    Integer addJobPlacement(Wyworkorganization wyworkorganization);

    //修改工作安排
    Integer updateJobPlacement(Wyworkorganization wyworkorganization);

    //删除工作安排
    Integer deleteJobPlacement(Wyworkorganization wyworkorganization);

    //工作提醒
    List reminderWork();

    //添加登记
    Integer addRegister(Wyregister wyregister);

    //空间情况
    List getSpaceInfo(@Param("registrationtype")String registrationType,@Param("timeType")Integer timeType,
                      @Param("plantingArea")String plantingArea);

    //查询字典表
    List getDictionaries();

    //查询可关联空间区域
    List getSpace();
}