package com.raysdata.riskmanagementserver.service;

import com.raysdata.riskmanagementserver.utils.PageBean;

import java.util.List;
import java.util.Map;

/**
 * @author zyy
 */
public interface RiskGridWarnService {

    /**
     * 各省市电网运行风险数
     * @param typecode
     * @return
     */
    List<Map<String, Object>> getSrpriskgridwarncnt(String typecode);

    /**
     * 电网运行风险等级汇总统计
     * @param dateType
     * @return
     */
    List<Map<String, Object>> getSrpriskgridwarnlevelcnt(int dateType);

    /**
     * 电网运行风险预警分页查询
     * @param paramJson
     * @return
     */
    PageBean<Map<String, Object>> getSrpriskgridwarnlist(String paramJson);

    /**
     * 电网运行风险预警详情
     * @param gridWarnNoticeId
     * @return
     */
    Map<String,Map<String,Object>> getSrpriskgridwarninfobygridwarnnoticeid(String gridWarnNoticeId);
}
