package com.property.mapper;

import com.property.entity.Wyequipmentstrategy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface WyequipmentstrategyMapper {

    //获取总数
    int getEquipmentStrategyCnt( @Param("policyname") String policyname,@Param("policy") String policy,
                                 @Param("planningtime") String planningtime,@Param("company") String company);

    //查询维保策略
    List<Map> getEquipmentStrategy( @Param("policyname") String policyname,@Param("policy") String policy,
                                    @Param("planningtime") String planningtime,@Param("company") String company,
                                    @Param("startIndex")int startIndex, @Param("pageSize")int pageSize);

    //添加维保策略
    void insertEquipmentStrategy( @Param("policyname") String policyname,@Param("policy") String policy,
                                  @Param("planningtime") String planningtime,@Param("planendtime") String planendtime,
                                  @Param("content") String content, @Param("resources") String resources,
                                  @Param("company") String company, @Param("delflag") String delflag,
                                  @Param("policyid") String policyid);
    //生成维保工单
    void insertMainOrder(@Param("workno") String workno);

    //查询修改维保策略
    Map getEquipmentStrategyById(@Param("id") Integer id);
    //修改维保策略
    void updateEquipmentStrategy(Wyequipmentstrategy wyequipmentstrategy);

    //删除维保策略
    void deleteEquipmentStrategy(@Param("id") Integer id);

    //获取策略分类
    List getPolicy();
}