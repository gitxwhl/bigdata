package com.property.mapper;

import com.property.entity.WyFrequencyDetails;
import com.property.entity.Wyinspectionplan;
import com.property.entity.Wyperson;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface WypersonMapper {

    //-----------------------------------------------排班计划-----------------------------------------------------
    //查询班次明细
    List getFrequency(@Param("date")String date);

    //添加班次明细
    void addFrequency(WyFrequencyDetails wyFrequencyDetails);

    //修改班次明细
    void updateFrequency(WyFrequencyDetails wyFrequencyDetails);

    //-----------------------------------------------巡检计划-----------------------------------------------------
    //查询列表数量
    Integer inspectionplanListCnt();
    //查询列表
    List inspectionplanList(@Param("index")Integer index,@Param("pageSize")Integer pageSize);

    //通过id查询门岗人员
    List getPersonById(@Param("ids")String ids);

    //新增巡检计划
    void addInspectionplan(Wyinspectionplan wyinspectionplan);

    //修改巡检计划
    void updateInspectionplan(Wyinspectionplan wyinspectionplan);

    //删除巡检计划
    void deleteInspectionplan(@Param("id")Integer id);

    //查询门岗人员
    List getPerson();

    //-----------------------------------------------岗位资料-----------------------------------------------------
    //新增
    void insertWyPerson(Wyperson wyperson);

    //查询列表数据个数
    int getPersonCnt();
    //查询列表数据
    List getPersonList(@Param("index") int startIndex, @Param("pageSize") int pageSize);

}