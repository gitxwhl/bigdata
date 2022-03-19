package com.raysdata.riskdataanalyzeserver.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author FangYF
 * @date 2021-08-05
 */
@Mapper
public interface RiskDataInfosMapper {
    /**
     * 数据治理count
     *
     * @param mapParam 动态条件
     * @return Integer
     */
    Integer getDataCountDataInfoManage(Map<String, Object> mapParam);

    /**
     * 数据治理list
     *
     * @param mapParam 动态条件
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getTableDataDataInfoManage(Map<String, Object> mapParam);

    /**
     * 数据清洗count
     *
     * @param mapParam 动态条件
     * @return Integer
     */
    Integer getDataCountDataInfoPurge(Map<String, Object> mapParam);

    /**
     * 数据清洗list
     *
     * @param mapParam 动态条件
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getTableDataDataInfoPurge(Map<String, Object> mapParam);

    /**
     * 数据加载count
     *
     * @param mapParam 动态条件
     * @return Integer
     */
    Integer getDataInfoLoadListCNT(Map<String, Object> mapParam);

    /**
     * 数据加载list
     *
     * @param mapParam 动态条件
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getDataInfoLoadList(Map<String, Object> mapParam);

    /**
     * 数据转换count
     *
     * @param mapParam 动态条件
     * @return Integer
     */
    Integer getDataInfoConversionListCNT(Map<String, Object> mapParam);

    /**
     * 数据转换list
     *
     * @param mapParam 动态条件
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getDataInfoConversionList(Map<String, Object> mapParam);
}
