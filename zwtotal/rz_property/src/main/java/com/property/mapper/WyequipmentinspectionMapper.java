package com.property.mapper;

import com.property.entity.Wyequipmentinspection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface WyequipmentinspectionMapper {

    //获取总数量
    int getTotalSize( @Param("company") String company,@Param("inspection") String inspection,
                      @Param("plantime") String plantime, @Param("begintime") String begintime);

    //获取设备巡检 信息
    List<Map> selectEquipmentInspection( @Param("company") String company, @Param("inspection") String inspection,
                                         @Param("plantime") String plantime, @Param("begintime") String begintime,
                                         @Param("startIndex")int startIndex, @Param("pageSize")int pageSize);

    //添加巡检计划信息
    int insertEquipmentInspection( @Param("inspection") String inspection,@Param("incequipment") String incequipment,
                                   @Param("plantime") String plantime,@Param("planendtime") String planendtime,
                                   @Param("planner") String planner,@Param("company") String company);

    //添加巡检记录信息
    String getIncEqu(@Param("inspection") String inspection);
    int updateEquipmentInspection( @Param("inspection") String inspection, @Param("begintime") String begintime,
                                   @Param("endtime") String endtime, @Param("inspectionperson") String inspectionperson,
                                   @Param("results") String results, @Param("remarks") String remarks,
                                   @Param("missing") String missing);

    //获取线路名称
    List getInspection();

    //获取涉及设备
    List getIncequipment();

    //获取巡检结果
    List getResults();
}