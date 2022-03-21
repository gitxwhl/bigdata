package com.property.mapper;

import com.property.entity.Wydutyrecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface WydutyrecordMapper {

    //添加值班记录
    Integer addDutyRecord(Wydutyrecord wydutyrecord);

    //查询值班记录数量
    Integer getDutyRecordCnt(@Param("beginTime")String beginTime,@Param("endTime")String endTime,
                             @Param("frequency")String frequency,@Param("person")String person);

    //查询值班记录
    List getDutyRecord(@Param("beginTime")String beginTime,@Param("endTime")String endTime,
                       @Param("frequency")String frequency,@Param("person")String person,
                       @Param("index")Integer index,@Param("pageSize")Integer pageSize);

    //查询人员
    List getPerson(@Param("isGate")String isGate);

    //查询字典表
    List dictionaries();
}