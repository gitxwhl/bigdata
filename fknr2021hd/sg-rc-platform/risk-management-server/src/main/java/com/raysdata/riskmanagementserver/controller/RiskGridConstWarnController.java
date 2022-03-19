package com.raysdata.riskmanagementserver.controller;


import com.nariit.rmcp.common.vo.WrappedResult;
import com.raysdata.riskmanagementserver.aop.LogAnnotation;
import com.raysdata.riskmanagementserver.service.RiskGridConstWarnServer;
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
@RequestMapping({"/riskGridConstWarnn"})
@Api("电网建设风险预警接口api")
@CrossOrigin
public class RiskGridConstWarnController {

    private static final String SESSION_TIMEOUT_TIP = "系统登录超时，请重新登录！";
    private final Logger log = LoggerFactory.getLogger(RiskGridConstWarnController.class);
    @Autowired
    private RiskGridConstWarnServer riskGridConstWarnServer;
    @ApiOperation("各地区电网建设风险预警数")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/srpRiskGridConstWarnAreaCNT"}
    )
    @LogAnnotation(module="各地区电网建设风险预警数",operator="查询",xtcs="two",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/电网建设风险预警可视化")
    public WrappedResult getSrpriskgridconstwarnareacnt(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.riskGridConstWarnServer.getSrpriskgridconstwarnareacnt(param));
        } catch (Exception var4) {
            this.log.error("获取各地区电网建设风险预警失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取各地区电网建设风险预警失败!");
        }
    }

    @ApiOperation("电网建设风险等级3、4级汇总警数")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/srpRiskGridConstWarnLevelCNT"}
    )
    @LogAnnotation(module="电网建设风险等级3、4级汇总警数",operator="查询",xtcs="one",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/电网建设风险预警可视化")
    public WrappedResult getSrpriskgridconstwarnlevelcnt(HttpServletRequest request, @RequestParam String areaId) {

        try {
            return WrappedResult.successWrapedResult(this.riskGridConstWarnServer.getSrpriskgridconstwarnlevelcnt(areaId));
        } catch (Exception var4) {
            this.log.error("获取电网建设风险等级3、4级汇总警数失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取电网建设风险等级3、4级汇总警数失败!");
        }
    }

    @ApiOperation("电网建设作业类型风险数量统计数")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/srpRiskGridConstWarnWorkCNT"}
    )
    @LogAnnotation(module="电网建设作业类型风险数量统计数",operator="查询",xtcs="one",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/电网建设风险预警可视化")
    public WrappedResult getSrpriskgridconstwarnworkcnt(HttpServletRequest request, @RequestParam String areaId) {

        try {
            return WrappedResult.successWrapedResult(this.riskGridConstWarnServer.getSrpriskgridconstwarnworkcnt(areaId));
        } catch (Exception var4) {
            this.log.error("获取电网建设作业类型风险数量统计数 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取电网建设作业类型风险数量统计数!");
        }
    }

    @ApiOperation("电网建设各市公司本周开始及解除风险数量统计数")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/srpRiskGridConstWarnBeginOrEndCNT"}
    )
    @LogAnnotation(module="电网建设各市公司本周开始及解除风险数量统计数",operator="查询",xtcs="one",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/电网建设风险预警可视化")
    public WrappedResult getSrpriskgridconstwarnbeginorendcnt(HttpServletRequest request, @RequestParam String areaId) {
        try {
            return WrappedResult.successWrapedResult(this.riskGridConstWarnServer.getSrpriskgridconstwarnbeginorendcnt(areaId));
        } catch (Exception var4) {
            this.log.error("获取电网建设各市公司本周开始及解除风险数量统计数失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取电网建设各市公司本周开始及解除风险数量统计数失败!");
        }
    }

    @ApiOperation("电网建设年度历史同期值风险数量统计数")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/srpRiskGridConstWarnYearCNT"}
    )
    @LogAnnotation(module="电网建设年度历史同期值风险数量统计数",operator="查询",xtcs="one",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/电网建设风险预警可视化")
    public WrappedResult getSrpriskgridconstwarnyearcnt(HttpServletRequest request, @RequestParam String areaId) {
        try {
            return WrappedResult.successWrapedResult(this.riskGridConstWarnServer.getSrpriskgridconstwarnyearcnt(areaId));
        } catch (Exception var4) {
            this.log.error("获取电网建设年度历史同期值风险数量统计数失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取电网建设年度历史同期值风险数量统计数失败!");
        }
    }

    @ApiOperation("电网建设地市作业计划列表")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/srpRiskGridConstWorkList"}
    )
    @LogAnnotation(module="电网建设地市作业计划列表",operator="查询",xtcs="onePage",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/电网建设风险预警可视化")
    public WrappedResult getSrpriskgridconstworklist(HttpServletRequest request, @RequestBody String param) {

        try {
            return WrappedResult.successWrapedResult(this.riskGridConstWarnServer.getSrpriskgridconstworklist(param));
        } catch (Exception var4) {
            this.log.error("获取电网建设地市作业计划列表失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取电网建设地市作业计划列表失败!");
        }
    }
    @ApiOperation("电网建设年作业计划详情查询")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/srpRiskGridConstWorkPlanInfo"}
    )
    @LogAnnotation(module="电网建设年作业计划详情查询",operator="查询",xtcs="workPlanIdOne",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块模块/安全风险全景感知/电网建设风险预警可视化")
    public WrappedResult getSrpriskgridconstworkplaninfo(HttpServletRequest request, @RequestParam String workPlanId) {
        try {
            return WrappedResult.successWrapedResult(this.riskGridConstWarnServer.getSrpriskgridconstworkplaninfo(workPlanId));
        } catch (Exception var4) {
            this.log.error("获取电网建设年作业计划详情查询失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取电网建设年作业计划详情查询失败!");
        }
    }

}
