package com.property.mapper;

import com.property.entity.SysParameter;
import com.property.entity.Wyirrigationmanagement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
@Mapper
public interface WyirrigationmanagementMapper {

    //新增计划
    int getNewlyIncreasedPlan( @Param("programname") String programname,
                               @Param("spacename") String spacename,
                               @Param("vegetationname") String vegetationname,
                               @Param("irrigationtime") String irrigationtime,
                               @Param("irrigationmethod") String irrigationmethod);
    //修改计划
    int getamendPlan(@Param("id") int id,
                     @Param("programname") String programname,
                     @Param("spacename") String spacename,
                     @Param("vegetationname") String vegetationname,
                     @Param("irrigationtime") String irrigationtime,
                     @Param("irrigationmethod") String irrigationmethod);

    //返现灌溉方式
    @Select(" SELECT parakey,paravalue FROM sys_parameter WHERE paraname = 'irrigationmethod';")
    List<SysParameter> getIrrigationMethods();

    //查询列表数量
    int getWyirrigationmanagementCnt(@Param("programname") String programname);

    //查询列表数据
    List getWyirrigationmanagementList(@Param("startIndex")  int startIndex,
                                       @Param("pageSize")  int pageSize,
                                       @Param("programname") String programname);


    //评估编辑
    @Update(" UPDATE wy_irrigationmanagement\n" +
            "        SET isplan = #{isplan},\n" +
            "            details = #{details},\n" +
            "            evaluation = #{evaluation},\n" +
            "            rategradestar = #{rategradestar},\n" +
            "            enclosure = #{enclosure}\n" +
            "        WHERE\n" +
            "\t\t    id = #{id}")
    int getAssessRedact(Wyirrigationmanagement wyirrigationmanagement);

    //计划详情
    @Select("SELECT\n" +
            "\t a.id,\n" +
            "\t\t\t\t\ta.programname,\n" +
            "\t\t\t\t\ta.spacename,\n" +
            "\t\t\t\t\ta.vegetationname,\n" +
            "\t\t\t\t\ta.irrigationtime,\n" +
            "\t\t\t\t\ta.irrigationmethod irrt,\n" +
            "\t\t\t\t\td.paravalue irrigationmethod,\n" +
            "\t\t\t\t\ta.evaluation,\n" +
            "\t\t\t\t\ta.isplan,\n" +
            "\t\t\t\t\ta.enclosure,\n" +
            "\t\t\t\t\ta.details,\n" +
            "\t\t\t\t\ta.rategradestar  \n" +
            "\t\tFROM\n" +
            "\t\twy_irrigationmanagement AS a\n" +
            "\t\tLEFT JOIN ( SELECT * FROM sys_parameter WHERE paraname = 'irrigationmethod' ) d ON a.irrigationmethod = d.parakey\n" +
            "\t\tWHERE\n" +
            "\t\ta.id = #{id}")
    Wyirrigationmanagement getPlanDetails(@Param("id") int id);


    //按时执行
    int  getexecute(   @Param("startTime") String startTime,
                       @Param("finishTime") String finishTime);
    //未按时执行
    int  getNotexecute(     @Param("startTime") String startTime,
                            @Param("finishTime") String finishTime);


    //一星
    int  getOneStar(
            @Param("startTime") String startTime,
            @Param("finishTime") String finishTime);
    //二星
    int  getTwoStar(
            @Param("startTime") String startTime,
            @Param("finishTime") String finishTime);
    //三星
    int  getThreeStar(
            @Param("startTime") String startTime,
            @Param("finishTime") String finishTime);

    //四星
    int  getFourStar(
            @Param("startTime") String startTime,
            @Param("finishTime") String finishTime);

    //五星
    int  getFiveStar(
            @Param("startTime") String startTime,
            @Param("finishTime") String finishTime);

}
