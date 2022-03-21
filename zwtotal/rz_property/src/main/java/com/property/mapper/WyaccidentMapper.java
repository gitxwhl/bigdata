package com.property.mapper;

import com.property.entity.Wyaccident;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface WyaccidentMapper {

    //添加事故记录
    Integer addAccident(Wyaccident wyaccident);

    //查询事故记录数量
    Integer getAccidentCnt(@Param("beginTime")String beginTime,@Param("endTime")String endTime);
    //查询事故记录
    List getAccident(@Param("beginTime")String beginTime,@Param("endTime")String endTime,
                        @Param("index")Integer index,@Param("pageSize")Integer pageSize);

    //通过人员id查询人员
    List getPersonById(@Param("ids")String ids);

}