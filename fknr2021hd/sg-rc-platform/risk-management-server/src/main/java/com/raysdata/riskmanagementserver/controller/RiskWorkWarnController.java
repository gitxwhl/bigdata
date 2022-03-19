package com.raysdata.riskmanagementserver.controller;

import com.nariit.rmcp.common.vo.WrappedResult;
import com.raysdata.riskmanagementserver.aop.LogAnnotation;
import com.raysdata.riskmanagementserver.service.RiskWorkWarnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
/**
 * @author zyy
 */
@RestController
@RequestMapping({"/riskWorkWarn"})
@Api("作业风险统计分析报告接口api")
@CrossOrigin
public class RiskWorkWarnController {

    private static final String SESSION_TIMEOUT_TIP = "系统登录超时，请重新登录！";
    private final Logger log = LoggerFactory.getLogger(RiskWorkWarnController.class);
    @Autowired
    private RiskWorkWarnService riskWorkWarnService;
    @ApiOperation("地图各单位风险预警情况统计")
    @RequestMapping(
            method = {RequestMethod.POST},
                value = {"/srpRiskWorkWarnAreaCNT"}
    )
    @LogAnnotation(module="地图各单位风险预警情况统计",operator="查询",xtcs="riskWarnAreaCNTTwo",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/风险作业统计分析/作业风险统计分析报告")
    public WrappedResult getSrpriskworkwarnareacnt(HttpServletRequest request, @RequestBody String paramJson) {
        WrappedResult wrappedResult = WrappedResult.successWrapedResult(this.riskWorkWarnService.getSrpriskworkwarnareacnt(paramJson));
       Boolean flag= wrappedResult.isSuccessful();
        if(flag){
                return wrappedResult;
                }
        return WrappedResult.failedWrappedResult("获取地图各单位风险预警情况统计失败!");
        /*try {
            return WrappedResult.successWrapedResult(this.riskWorkWarnService.getSrpriskworkwarnareacnt(paramJson));
        } catch (Exception var4) {
            this.log.error("获取地图各单位风险预警情况统计失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取地图各单位风险预警情况统计失败!");
        }*/
    }

    @ApiOperation("电网运行风险等级汇总")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/srpRiskGridWarnLevelCNT"}
    )
    @LogAnnotation(module="电网运行风险等级汇总",operator="查询",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/风险作业统计分析/作业风险统计分析报告")
    public WrappedResult getSrpriskgridwarnlevelcnt(HttpServletRequest request) {
        try {
            return WrappedResult.successWrapedResult(this.riskWorkWarnService.getSrpriskgridwarnlevelcnt());
        } catch (Exception var4) {
            this.log.error("获取电网运行风险等级汇总失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取电网运行风险等级汇总失败!");
        }
    }

    @ApiOperation("产业安全风险预警数量统计")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/srpRiskIndusRiskWarnCNT"}
    )
    @LogAnnotation(module="产业安全风险预警数量统计",operator="查询",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/风险作业统计分析/作业风险统计分析报告")
    public WrappedResult getRiskindusriskwarncnt(HttpServletRequest request) {

        try {
            return WrappedResult.successWrapedResult(this.riskWorkWarnService.getRiskindusriskwarncnt());
        } catch (Exception var4) {
            this.log.error("获取产业安全风险预警数量统计失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取产业安全风险预警数量统计失败!");
        }
    }

    @ApiOperation("网络预警各维度数量统计")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/srpRiskEventTypeCNT"}
    )
    @LogAnnotation(module="网络预警各维度数量统计",operator="查询",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/风险作业统计分析/作业风险统计分析报告")
    public WrappedResult getSrpriskeventtypecnt(HttpServletRequest request) {

        try {
            return WrappedResult.successWrapedResult(this.riskWorkWarnService.getSrpriskeventtypecnt());
        } catch (Exception var4) {
            this.log.error("获取网络预警各维度数量统计数失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取网络预警各维度数量统计数失败!");
        }
    }

    @ApiOperation("电网建设风险等级统计")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/srpSrpRiskGridConstWarnLevelCNT"}
    )
    @LogAnnotation(module="电网建设风险等级统计",operator="查询",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/风险作业统计分析/作业风险统计分析报告")
    public WrappedResult getSrpriskgridconstwarnlevelcnt(HttpServletRequest request) {
        try {
            return WrappedResult.successWrapedResult(this.riskWorkWarnService.getSrpriskgridconstwarnlevelcnt());
        } catch (Exception var4) {
            this.log.error("获取电网建设风险等级统计失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取电网建设风险等级统计失败!");
        }
    }
}
