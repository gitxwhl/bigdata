package com.raysdata.riskmanagementserver.controller;

import com.nariit.rmcp.common.vo.WrappedResult;
import com.raysdata.riskmanagementserver.aop.LogAnnotation;
import com.raysdata.riskmanagementserver.service.RiskManagementService;
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
@RequestMapping({"/riskManagement"})
@Api("安全风险综合可视化接口api")
@CrossOrigin
public class RiskManagementController {

    private static final String SESSION_TIMEOUT_TIP = "系统登录超时，请重新登录！";
    private final Logger log = LoggerFactory.getLogger(RiskManagementController.class);

    @Autowired
    RiskManagementService riskManagementService;

    @ApiOperation("各风险总数统计")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/riskManagementCNT"}
    )
    @LogAnnotation(module="各风险总数统计",operator="查询",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/安全风险综合可视化")
    public WrappedResult get(HttpServletRequest request) {
        try {
            return WrappedResult.successWrapedResult(this.riskManagementService.getRiskmanagementcnt());
        } catch (Exception var4) {
            log.error("获取各风险总数汇总数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取各风险总数汇总数据失败!");
        }
    }

    @ApiOperation("各电压等级作业风险数")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/workplanVoltageLevelCNT"}
    )
    @LogAnnotation(module="各电压等级作业风险数",operator="查询",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/安全风险综合可视化")
    public WrappedResult getSrpworkplanvoltagelevelcnt(HttpServletRequest request) {

        try {
            return WrappedResult.successWrapedResult(this.riskManagementService.getSrpworkplanvoltagelevelcnt());
        } catch (Exception var4) {
            log.error("获取各电压等级作业风险数数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取各电压等级作业风险数数据失败!");
        }
    }
    @ApiOperation("电网运行风险等级")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/riskGridWarnLevelCNT"}
    )
    @LogAnnotation(module="电网运行风险等级",operator="查询",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/安全风险综合可视化")
    public WrappedResult getSrpriskgridwarnlevelcnt(HttpServletRequest request) {

        try {
            return WrappedResult.successWrapedResult(this.riskManagementService.getSrpriskgridwarnlevelcnt());
        } catch (Exception var4) {
            log.error("获取电网运行风险等级数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取电网运行风险等级数据失败!");
        }
    }
    @ApiOperation("电网建设风险等级")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/riskGridConstWarnLevelCNT"}
    )
    @LogAnnotation(module="电网建设风险等级",operator="查询",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/安全风险综合可视化")
    public WrappedResult getSrpriskgridconstwarnlevelcnt(HttpServletRequest request) {

        try {
            return WrappedResult.successWrapedResult(this.riskManagementService.getSrpriskgridconstwarnlevelcnt());
        } catch (Exception var4) {
            log.error("获取电网建设风险等级数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取电网建设风险等级数据失败!");
        }
    }
    @ApiOperation("产业风险等级")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/riskIndusRiskWarnLevelCNT"}
    )
    @LogAnnotation(module="产业风险等级",operator="查询",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/安全风险综合可视化")
    public WrappedResult getSrpriskindusriskwarnlevelcnt(HttpServletRequest request) {

        try {
            return WrappedResult.successWrapedResult(this.riskManagementService.getSrpriskindusriskwarnlevelcnt());
        } catch (Exception var4) {
            log.error("获取产业风险等级数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取产业风险等级数据失败!");
        }
    }
    @ApiOperation("网络安全风险等级")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/riskEventWarningLevelCNT"}
    )
    @LogAnnotation(module="网络安全风险等级",operator="查询",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/安全风险综合可视化")
    public WrappedResult getSrpriskeventwarninglevelcnt(HttpServletRequest request) {

        try {
            return WrappedResult.successWrapedResult(this.riskManagementService.getSrpriskeventwarninglevelcnt());
        } catch (Exception var4) {
            log.error("获取网络安全风险等级数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取网络安全风险等级数据失败!");
        }
    }
    @ApiOperation("安全风险综合情况")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/riskWarnAreaCNT"}
    )
    @LogAnnotation(module="安全风险综合情况",operator="查询",xtcs="riskWarnAreaCNTThree",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/安全风险综合可视化")
    public WrappedResult getSrpriskwarnareacnt(HttpServletRequest request, @RequestBody String param) throws Throwable{
        try {
            return WrappedResult.successWrapedResult(this.riskManagementService.getSrpriskwarnareacnt(param));
        } catch (Exception var4) {
            log.error("获取安全风险综合情况数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取安全风险综合情况数据失败!");
        }
    }
}
