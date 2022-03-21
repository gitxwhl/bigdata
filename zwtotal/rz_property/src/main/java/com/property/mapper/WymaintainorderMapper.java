package com.property.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface WymaintainorderMapper {

    //获取维护工单总数
    int getMaintainorderCnt( @Param("company") String company, @Param("policyname") String policyname,
                             @Param("policy") String policy, @Param("state") String state);

    //查询维护工单
    List<Map> getMaintainorder( @Param("company") String company, @Param("policyname") String policyname,
                                @Param("policy") String policy, @Param("state") String state,
                                @Param("startIndex")int startIndex, @Param("pageSize")int pageSize);

    //获取工单状态
    List getWorkState();

    //获取工单详情信息
    List<Map> getMaintainorderDetails( @Param("id") Integer id);

    //修改派单信息
    String getSingleState(@Param("id") Integer id);
    //修改未派单的信息
    void updateSingleOne(@Param("planningtime") String planningtime,@Param("receiving") String receiving,
                         @Param("personnel") String personnel,@Param("state") String state,@Param("id") Integer id);
    //修改已派单的信息
    void updateSingleTwo(@Param("operationrecord") String operationrecord, @Param("defectidentification") String defectidentification,
                         @Param("completiontime") String completiontime,
                         @Param("state") String state,@Param("id") Integer id);

    //生成超期订单
    void updateBeyondOrder();

    //查询超期订单总数
    int getBeyondOrderCnt();

    //查询超期订单
    List<Map> getBeyondOrder( @Param("startIndex")int startIndex, @Param("pageSize")int pageSize);

    //重新生成超期订单告警
    void reOrderAlarm(@Param("policyname") String policyname,@Param("policy") String policy,
                      @Param("planningtime") String planningtime,@Param("planendtime") String planendtime,
                      @Param("content") String content,@Param("resources") String resources,
                      @Param("armstatus") String armstatus,@Param("id") Integer id);
    //取消超期订单告警
    void updateOrderAlarm( @Param("id") Integer id);
}