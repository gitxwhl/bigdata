package com.raysdata.riskmanagementserver.service;

import java.util.List;
import java.util.Map;
/**
 * @author zyy
 */
public interface RiskManagementService {
    /**
     * 各风险总数统计
     * @return
     */
    Map<String, Object> getRiskmanagementcnt();
    /**
     *各电压等级作业风险数
     * @return
     */
    Map<String, Object> getSrpworkplanvoltagelevelcnt();
    /**
     *电网运行风险等级
     * @return
     */
    Map<String, Object> getSrpriskgridwarnlevelcnt();
    /**
     *电网建设风险等级
     * @return
     */
    Map<String, Object> getSrpriskgridconstwarnlevelcnt();
    /**
     *产业风险等级
     * @return
     */
    Map<String, Object> getSrpriskindusriskwarnlevelcnt();
    /**
     *网络安全风险等级
     * @return
     */
    Map<String, Object> getSrpriskeventwarninglevelcnt();
    /**
     *安全风险综合情况
     * @return
     * @param param
     * @throws Exception 异常处理
     */
    List<Map<String, Object>> getSrpriskwarnareacnt(String param) throws Throwable;

}
