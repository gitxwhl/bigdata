package com.property.mapper;

import com.property.entity.Wycleaningplan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface WyCleanPlanMapper {
    //获取保洁计划总数
    int getPlanSize(@Param("company") String company,@Param("planname") String planname,
                    @Param("plantype") String plantype,@Param("cleaningarea") String cleaningarea,
                    @Param("cycle") String cycle);

    //查询保洁计划
    List selectCleanPlan(@Param("company") String company,@Param("planname") String planname,
                         @Param("plantype") String plantype,@Param("cleaningarea") String cleaningarea,
                         @Param("cycle") String cycle,@Param("pageSize") Integer pageSize,
                         @Param("startIndex") Integer startIndex);
    //根据保洁计划id查询相关内容
    List selectCleanContent(@Param("id") Integer id);

    //删除保洁计划
    void deleteCleanPlan(@Param("id") Integer id);

    //修改根据id返回保洁计划
    Map updateCleanPlan(@Param("id") Integer id);
    //修改根据id返回保洁内容
    List updateContent(@Param("id") Integer id);
    //修改根据id返回保洁方案
    List updateProgram(@Param("id") Integer id);

    //修改保洁方案
    void alterPlan(@Param("id") Integer id,@Param("planname") String planname,
                   @Param("cleaningtime") String cleaningtime,@Param("plantype") String plantype);
    //修改保洁内容
    void alterContent(@Param("id") Integer id,@Param("standards") String standards,
                      @Param("contentrange") String contentrange,
                      @Param("cleaningarea") String cleaningarea,@Param("cycle") String cycle);
    //修改保洁方案
    void alterProgram(@Param("id") Integer id,@Param("cleaningarea") String cleaningarea,
                      @Param("personnel") String personnel,@Param("remarks") String remarks);

    //新增保洁计划
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertPlan(Wycleaningplan wycleaningplan);

    //新增保洁内容
    void insertContent(@Param("id") Integer id,@Param("standards") String standards,
                      @Param("contentrange") String contentrange,
                      @Param("cleaningarea") String cleaningarea,@Param("cycle") String cycle);
    //新增保洁方案
    void insertProgram(@Param("id") Integer id,@Param("cleaningarea") String cleaningarea,
                      @Param("personnel") String personnel,@Param("remarks") String remarks);

    //查询检查计划总数

    //查询检查计划

    //删除检查计划
    void deleteInspectionPlan(@Param("id") Integer id);

    //修改-根据id查询信息
    Map selectInspectionPlanById(@Param("id") Integer id);
}
