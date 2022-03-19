package com.raysdata.riskmanagementserver.dao;

import com.raysdata.riskmanagementserver.bean.vo.SrpRiskGridwannoticeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Mir.Li
 * @date 2020/4/13  -  12:55
 */
@Mapper
public interface RiskGridWarnDao {
    /**
     *各省市电网运行风险数
     * @param
     * @return
     */
    List<Map<String, Object>> getSrpriskgridwarncnt(String warninglevel);
    /**
     *电网运行风险等级汇总统计
     * @param
     * @return
     */
    List<Map<String, Object>> getSrpriskgridwarnlevelcnt(Integer dateType);
    /**
     *获取
     * @param
     * @return
     */
    Map<String, Object> getSrpriskgridwarnworkcnt(Integer dateType);
    /**
     *电网运行风险预警分页查询数量统计
     * @param
     * @return
     */
    Integer getSrpriskgridwarnlistcnt(@Param("srpRiskGridwannoticeVo") SrpRiskGridwannoticeVo srpRiskGridwannoticeVo);
    /**
     *电网运行风险预警分页查询
     * @param
     * @return
     */
    List<Map<String, Object>> getSrpriskgridwarnlist(@Param("srpRiskGridwannoticeVo") SrpRiskGridwannoticeVo srpRiskGridwannoticeVo);
    /**
     *电网运行风险预警详情
     * @param gridWarnNoticeId
     * @return
     */
    Map<String, Object> getSrpriskgridwarninfo(String gridWarnNoticeId);
    /**
     *获取
     * @param gridWarnNoticeId
     * @return
     */
    Map<String, Object> getSrpriskgridwarnfeedbackinfo(String gridWarnNoticeId);
    /**
     *获取
     * @param gridWarnNoticeId
     * @return
     */
    Map<String, Object> getSrpriskgridwarnreportinfo(String gridWarnNoticeId);
    /**
     *获取
     * @param gridWarnNoticeId
     * @return
     */
    Map<String, Object> getSrpriskgridwarnforminfo(String gridWarnNoticeId);
    /**
     *获取
     * @param gridWarnNoticeId
     * @return
     */
    Map<String, Object> getSrpriskgridwarnrelinfo(String gridWarnNoticeId);
}
