package com.raysdata.riskdataanalyzeserver.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author FangYF
 * @date 2021-08-06
 */
@Mapper
@Repository
public interface ToolMapper {

    /**
     * 按照类型统计工器具数量
     *
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getToolListByType();

    /**
     * 按类型统计库存工器具数量
     *
     * @param CREATE_TIME 时间
     * @return Map<String, Object>
     */
    Map<String, Object> getCurveMap1(@Param("CREATE_TIME") String CREATE_TIME);

    /**
     * 按类型统计报废数量
     *
     * @param CREATE_TIME 时间
     * @return Map<String, Object>
     */
    Map<String, Object> getCurveMap2(@Param("CREATE_TIME") String CREATE_TIME);

    /**
     * 按类型统计检测工器具数量
     *
     * @param CREATE_TIME 时间
     * @return Map<String, Object>
     */
    Map<String, Object> getCurveMap3(@Param("CREATE_TIME") String CREATE_TIME);

    /**
     * 按类型统计检测工器具数量曲线
     *
     * @param CREATE_TIME 时间
     * @return Map<String, Object>
     */
    Map<String, Object> getCurveMapByType(@Param("CREATE_TIME") String CREATE_TIME);


    /**
     * 实时预警count
     *
     * @param mapParam 动态SQL
     * @return Integer
     */
    Integer getWarningCNT(Map<String, Object> mapParam);

    /**
     * 实时预警list
     *
     * @param mapParam 动态SQL
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getWarningList(Map<String, Object> mapParam);

    /**
     * 查询生产日期和保质期
     *
     * @param mapParam 动态SQL
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getProductionAndShelfList(Map<String, Object> mapParam);


    /**
     * 查询评价列表的数据count
     *
     * @param mapParam 动态SQL
     * @return Integer
     */
    Integer getAppraiseCNT(Map<String, Object> mapParam);

    /**
     * 查询评价列表的数据list
     *
     * @param mapParam 动态SQL
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getAppraiseList(Map<String, Object> mapParam);

    /**
     * 查询评价TOP 10 （BOTTOM 10 为desc）
     *
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getTop10List();

    /**
     * 查询评价TOP 10 BOTTOM （BOTTOM 为desc）
     *
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getTop10BOTTOMList();
}
