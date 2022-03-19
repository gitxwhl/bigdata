
package com.raysdata.riskmanagementserver.controller;

import com.nariit.rmcp.common.vo.WrappedResult;
import com.raysdata.riskmanagementserver.aop.LogAnnotation;
import com.raysdata.riskmanagementserver.service.RiskGridWarnService;
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
@RequestMapping({"/riskGridWarn"})
@Api("电网运行风险综合可视化接口api")
@CrossOrigin
public class RiskGridWarnController {
    private static final String SESSION_TIMEOUT_TIP = "系统登录超时，请重新登录！";
    private final Logger log = LoggerFactory.getLogger(RiskGridWarnController.class);
    @Autowired
    private RiskGridWarnService riskGridWarnService;


    @ApiOperation("各省市电网运行风险数")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/srpRiskGridWarnCNT/{warningLevel}"}
    )
    @LogAnnotation(module="各省市电网运行风险数",operator="查询",xtcs="warningLevelOne",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/电网运行风险预警可视化")
    public WrappedResult getSrpriskgridwarncnt(HttpServletRequest request, @PathVariable(value = "warningLevel",required = false) String warningLevel) {
        try {
            return WrappedResult.successWrapedResult(this.riskGridWarnService.getSrpriskgridwarncnt(warningLevel));
        } catch (Exception var4) {
            this.log.error("获取各省市电网运行风险数失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取各省市电网运行风险数失败!");
        }
    }
    @ApiOperation("电网运行风险等级汇总统计")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/riskGridWarnTabCNT/{dateType}"}
    )
    @LogAnnotation(module="电网运行风险等级汇总统计",operator="查询",xtcs="dateTypelOne",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/电网运行风险预警可视化")
    public WrappedResult getSrpriskgridwarnlevelcnt(HttpServletRequest request, @PathVariable("dateType") int dateType) {

        try {
            return WrappedResult.successWrapedResult(this.riskGridWarnService.getSrpriskgridwarnlevelcnt(dateType));
        } catch (Exception var4) {
            this.log.error("获取电网运行风险等级汇总统计失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取电网运行风险等级汇总统计失败!");
        }
    }

    @ApiOperation("电网运行风险预警分页查询")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/riskGridWarnList"}
    )
    @LogAnnotation(module="电网运行风险预警分页查询",operator="查询",xtcs="ninePage",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/电网运行风险预警可视化")
    public WrappedResult getSrpriskgridwarnlist(HttpServletRequest request, @RequestBody String param) {
        try {
            WrappedResult wrappedResult  = WrappedResult.successWrapedResult(this.riskGridWarnService.getSrpriskgridwarnlist(param));
            return wrappedResult;
        } catch (Exception var4) {
            this.log.error("获取电网运行风险预警分页查询失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取电网运行风险预警分页查询失败!");
        }
    }
    @ApiOperation("电网运行风险预警详情")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/riskGridWarnInfo/{gridWarnNoticeId}"}
    )
    @LogAnnotation(module="电网运行风险预警详情",operator="查询",xtcs="gridWarnNoticeIdOne",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/电网运行风险预警可视化")
    public WrappedResult getSrpriskgridwarninfobygridwarnnoticeid(HttpServletRequest request, @PathVariable("gridWarnNoticeId") String gridWarnNoticeId) {
        try {
            return WrappedResult.successWrapedResult(this.riskGridWarnService.getSrpriskgridwarninfobygridwarnnoticeid(gridWarnNoticeId));
        } catch (Exception var4) {
            this.log.error("获取电网运行风险预警详情查询失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取电网运行风险预警详情查询失败!");
        }
    }
}
