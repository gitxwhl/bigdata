package com.raysdata.riskmanagementserver.dao;

import com.raysdata.riskmanagementserver.bean.vo.SrpRiskSysTabVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface RiskEventWarnDao {
    /**
     * 地图各单位网络风险数量统计
     * @param srpRiskSysTabVo
     * @author zyyy
     * @return
     */
    List<Map<String, Object>> getSrpriskeventwarnareacnt(@Param("srpRiskSysTabVo")SrpRiskSysTabVo srpRiskSysTabVo);

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
