package com.raysdata.riskmanagementserver.service;

import java.util.List;
import java.util.Map;
/**
 * @author zyy
 */
public interface RiskWorkWarnService {
    /**
     * 地图各单位风险预警情况统计
     * @param paramJson
     * @return
     */
    List<Map<String, Object>> getSrpriskworkwarnareacnt(String paramJson);
    /**
     *电网运行风险等级汇总
     * @param
     * @return
     */
    List<Map<String, Object>> getSrpriskgridwarnlevelcnt();
    /**
     *产业安全风险预警数量统计
     * @param
     * @return
     */
    Map<String, Object> getRiskindusriskwarncnt();
    /**
     *网络预警各维度数量统计
     * @param
     * @return
     */
    Map<String, Object> getSrpriskeventtypecnt();
    /**
     *电网建设风险等级统计
     * @param
     * @return
     */
    Map<String, Object> getSrpriskgridconstwarnlevelcnt();
}
