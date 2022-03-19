package com.raysdata.riskmanagementserver.service;

import com.raysdata.riskmanagementserver.utils.PageBean;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
/**
 * @author zyy
 */
public interface RiskWorkWarnForAllService {
    /**
     * 地图各单风险作业预警情况
     * @param paramJson
     * @return
     */
    List<Map<String, Object>> getSrpriskworkwarnforallareacnt(String paramJson);
    /**
     *作业类型风险数量统计
     * @param areaId
     * @return
     */
    Map<String, Object> getSrpriskworkwarnforallworktypecnt(String areaId);
    /**
     *风险作业状态数量统计
     * @param areaId
     * @return
     */
    Map<String, Object> getSrpriskworkwarnworkstatecnt(String areaId);
    /**
     *各省公司作业风险数量统计和各省公司作业电网风险数量统计
     * @param paramJson
     * @return
     */
    List<Map<String, Object>> getSrpriskworkwarnareacnt(String paramJson);
    /**
     *地市作业计划详情列表
     * @param paramJson
     * @return
     */
    PageBean<Map<String, Object>> getSrpriskgridconstworklist(String paramJson);
    /**
     *作业计划详情查询
     * @param workPlanId
     * @return
     */
    Map<String, Object> getSrpriskworkplaninfo(String workPlanId);
    /**
     *视频下载
     * @param httpUrl
     * @param saveFile
     * @return
     * @throws MalformedURLException 抛出异常
     */
//    boolean  httpDownload(String httpUrl, String saveFile) throws MalformedURLException;
}
