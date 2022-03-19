package com.raysdata.riskmanagementserver.service;

import com.raysdata.riskmanagementserver.utils.PageBean;

import java.util.List;
import java.util.Map;


/*
 * 电网建设风险预警页面
 * */
/**
 * @author zyy
 */
public interface RiskGridConstWarnServer {

    /**
     * 电网建设风险预警情况
     * warningLevel:风险等级 （默认不传查全量）
     * areaId：地区编码（全国地图查询默认可以不传）
     * @param paramJson
     * @return
     * */
    List<Map<String, Object>> getSrpriskgridconstwarnareacnt(String paramJson);


    /**
     * 3级风险和4级风险饼图模块
     * areaId：地区编码（全国地图查询默认可以不传）
     * @param areaId
     * @return
     * */
    Map<String, Object> getSrpriskgridconstwarnlevelcnt(String areaId);


    /**
     * 作业类型风险数量统计
     * areaId：地区编码（全国地图查询默认可以不传）
     * @param areaId
     * @return
     * */
    List<Map<String, Object>> getSrpriskgridconstwarnworkcnt(String areaId);


    /**
     * 各省公司本周开始及解除风险数量统计
     * areaId：地区编码（全国地图查询默认可以不传）
     * @param areaId
     * @return
     * */
    Map<String,Object> getSrpriskgridconstwarnbeginorendcnt(String areaId);


    /**
     * 年度历史同期值风险数量统计
     * areaId：地区编码（全国地图查询默认可以不传）
     * @param areaId
     * @return
     * */
    Map<String,Object> getSrpriskgridconstwarnyearcnt(String areaId) ;


    /**
     * 市电网建设风险预警情况（详情列表）
     * gridConstWarnNoticeId：输变电工程施工风险预警通知单ID
     * @param paramJson
     * @return
     * */
    PageBean<Map<String, Object>> getSrpriskgridconstworklist(String paramJson);


    /**
     * 作业详情页面
     * gridConstWarnNoticeId：输变电工程施工风险预警通知单ID
     * @param workPlanId
     * @return
     * */
    Map<String, Object> getSrpriskgridconstworkplaninfo(String workPlanId);


}
