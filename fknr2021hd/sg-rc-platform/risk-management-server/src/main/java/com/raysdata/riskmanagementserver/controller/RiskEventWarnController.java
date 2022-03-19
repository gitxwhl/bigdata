package com.raysdata.riskmanagementserver.controller;

import com.nariit.rmcp.common.vo.WrappedResult;
import com.raysdata.riskmanagementserver.aop.LogAnnotation;
import com.raysdata.riskmanagementserver.service.RiskEventWarnServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zyyy
 */
@RestController
@RequestMapping({"/riskEventWarn"})
@CrossOrigin
@Api("网络安全风险预警接口api")
public class RiskEventWarnController {


    private static final String SESSION_TIMEOUT_TIP = "系统登录超时，请重新登录！";
    private final Logger log = LoggerFactory.getLogger(RiskEventWarnController.class);

    @Autowired
    private RiskEventWarnServer riskEventWarnServer;

    @ApiOperation("地图各单位网络风险数量统计")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/srpRiskEventWarnAreaCNT"}
    )
    @LogAnnotation(module="地图各单位网络风险数量统计",operator="查询",xtcs="five",AllXtcs="安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/网络安全风险")
    public WrappedResult getSrpriskeventwarnareacnt(HttpServletRequest request,@RequestBody String paramJson) {
        try {
            return WrappedResult.successWrapedResult(this.riskEventWarnServer.getSrpriskeventwarnareacnt(paramJson));
        } catch (Exception var4) {
            this.log.error("获取地图各单位网络风险数量统计数失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取地图各单位网络风险数量统计数失败!");
        }
    }

    @ApiOperation("网络预警各维度数量统计")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/srpRiskEventTypeCNT"}
    )
    @LogAnnotation(module="网络预警各维度数量统计",operator="查询",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/网络安全风险")
    public WrappedResult getSrpriskeventtypecnt(HttpServletRequest request) {

        try {
            return WrappedResult.successWrapedResult(this.riskEventWarnServer.getSrpriskeventtypecnt());
        } catch (Exception var4) {
            this.log.error("获取网络预警各维度数量统计数失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取网络预警各维度数量统计数失败!");
        }
    }

    @ApiOperation("攻击威胁类型占比情况数")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/srpRiskEventWarnAttackThreatCNT"}
    )
    @LogAnnotation(module="攻击威胁类型占比情况数",operator="查询",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/网络安全风险")
    public WrappedResult getSrpriskeventwarnattackthreatcnt(HttpServletRequest request) {
        try {
            return WrappedResult.successWrapedResult(this.riskEventWarnServer.getSrpriskeventwarnattackthreatcnt());
        } catch (Exception var4) {
            this.log.error("获取攻击威胁类型占比情况数失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取攻击威胁类型占比情况数失败!");
        }
    }

    @ApiOperation("违规行为类型占比情况数")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/srpRiskEventWarnViolationCNT"}
    )
    @LogAnnotation(module="违规行为类型占比情况数",operator="查询",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/网络安全风险")
    public WrappedResult getSrpriskeventwarnviolationcnt(HttpServletRequest request) {

        try {
            return WrappedResult.successWrapedResult(this.riskEventWarnServer.getSrpriskeventwarnviolationcnt());
        } catch (Exception var4) {
            this.log.error("获取违规行为类型占比情况数失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取违规行为类型占比情况数失败!");
        }
    }

    @ApiOperation("各单位网络风险数量统计")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/srpRiskEventWarnCNT"}
    )
    @LogAnnotation(module="各单位网络风险数量统计",operator="查询",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/网络安全风险")
    public WrappedResult getSrpriskeventwarncnt(HttpServletRequest request) {

        try {
            return WrappedResult.successWrapedResult(this.riskEventWarnServer.getSrpriskeventwarncnt());
        } catch (Exception var4) {
            this.log.error("获取各单位网络风险数量统计数失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取各单位网络风险数量统计数失败!");
        }
    }



    /**
     * 查询省份信息
     */
    @ApiOperation("查询省份信息")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getProvinceList"}
    )
    @LogAnnotation(module="查询省份信息",operator="查询",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/电网建设风险预警可视化")
    public WrappedResult getProvinceList(HttpServletRequest request){
        try {
            return WrappedResult.successWrapedResult(this.riskEventWarnServer.getProvinceList());
        } catch (Exception var4) {
            this.log.error("获取各单位网络风险数量统计数失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取各单位网络风险数量统计数失败!");
        }

    }

}
