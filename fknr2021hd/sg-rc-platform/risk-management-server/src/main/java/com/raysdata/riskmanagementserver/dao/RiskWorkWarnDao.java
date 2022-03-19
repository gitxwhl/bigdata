package com.raysdata.riskmanagementserver.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * @author zyy
 */
@Mapper
public interface RiskWorkWarnDao {
    /**
     * 地图各单位风险预警情况统计
     * @param
     * @return
     */
    List<Map<String, Object>> getSrpriskworkwarnareacnt(@Param("warningLevel") String warningLevel, @Param("warnType")String warnType);
    /**
     *获取
     * @param
     * @return
     */
    List<Map<String, Object>> getSrpriskindusriskwarnareacnt(@Param("warningLevel")String warningLevel);
    /**
     *电网建设风险等级统计
     * @param
     * @return
     */
    Map<String, Object> getSrpriskgridconstwarnlevelcnt();
    /**
     *产业安全风险预警数量统计
     * @param
     * @return
     */
    Map<String, Object> getRiskindusriskwarncnt();
}
