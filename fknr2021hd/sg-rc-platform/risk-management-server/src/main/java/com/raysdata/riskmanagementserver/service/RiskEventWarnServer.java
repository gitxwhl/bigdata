package com.raysdata.riskmanagementserver.service;

import java.util.List;
import java.util.Map;
/**
 * @author zyy
 */
public interface RiskEventWarnServer {
    /**
     * 地图各单位网络风险数量统计
     * @param paramJson
     * @return
     */
    List<Map<String, Object>> getSrpriskeventwarnareacnt(String paramJson);
    /**
     *网络预警各维度数量统计
     * @param
     * @return
     */
    Map<String, Object> getSrpriskeventtypecnt();
    /**
     *攻击威胁类型占比情况数
     * @param
     * @return
     */
    Map<String, Object> getSrpriskeventwarnattackthreatcnt();
    /**
     *违规行为类型占比情况数
     * @param
     * @return
     */

    Map<String, Object> getSrpriskeventwarnviolationcnt();

    /**
     *各单位网络风险数量统计
     * @param
     * @return
     */
    List<Map<String, Object>> getSrpriskeventwarncnt();
    /**
     * 查询省份信息
     */
    List<Map<String,Object>> getProvinceList();
}
