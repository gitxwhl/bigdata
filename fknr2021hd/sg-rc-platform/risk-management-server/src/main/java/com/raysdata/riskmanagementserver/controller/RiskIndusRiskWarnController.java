package com.raysdata.riskmanagementserver.controller;

import com.nariit.rmcp.common.vo.WrappedResult;
import com.raysdata.riskmanagementserver.aop.LogAnnotation;
import com.raysdata.riskmanagementserver.service.RiskIndusRiskWarnService;
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
@RequestMapping({"/riskIndusRiskWarn"})
@Api("产业安全风险综合可视化接口api")
@CrossOrigin
public class RiskIndusRiskWarnController {

    private static final String SESSION_TIMEOUT_TIP = "系统登录超时，请重新登录！";
    private final Logger log = LoggerFactory.getLogger(RiskIndusRiskWarnController.class);
    @Autowired
    private RiskIndusRiskWarnService riskIndusRiskWarnService;


    @ApiOperation("各省市产业安全行风险数")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/srpRiskIndusRiskWarnAreaCNT"}
    )
    @LogAnnotation(module="各省市产业安全行风险数",operator="查询",xtcs="srpRiskIndusRiskWarnAreaCNTThree",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/产业安全风险")
    public WrappedResult getSrpriskindusriskwarnareacnt(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.riskIndusRiskWarnService.getSrpriskindusriskwarnareacnt(param));
        } catch (Exception var4) {
            this.log.error("获取各省市产业安全行风险数 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取各省市产业安全行风险数!");
        }
    }
    @ApiOperation("企业分布情况汇总数")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/srpRiskGridWarnCNT"}
    )
    @LogAnnotation(module="企业分布情况汇总数",operator="查询",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/产业安全风险")
    public WrappedResult getSrpriskgridwarncnt(HttpServletRequest request) {
        try {
            return WrappedResult.successWrapedResult(this.riskIndusRiskWarnService.getSrpriskdkycompanycnt());
        } catch (Exception var4) {
            this.log.error("获取企业分布情况汇总数失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取企业分布情况汇总数失败!");
        }
    }
    @ApiOperation("人员构成汇总数")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/srpRiskDkyCompanyStaffCNT"}
    )
    @LogAnnotation(module="人员构成汇总数",operator="查询",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/产业安全风险")
    public WrappedResult getSrpriskdkycompanystaffcnt(HttpServletRequest request) {

        try {
            return WrappedResult.successWrapedResult(this.riskIndusRiskWarnService.getSrpriskdkycompanystaffcnt());
        } catch (Exception var4) {
            this.log.error("获取人员构成汇总数失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取人员构成汇总数失败!");
        }
    }
    @ApiOperation("产业安全风险预警数")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/riskIndusRiskWarnCNT"}
    )
    @LogAnnotation(module="产业安全风险预警数",operator="查询",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/产业安全风险")
    public WrappedResult getRiskindusriskwarncnt(HttpServletRequest request) {

        try {
            WrappedResult wrappedResult= WrappedResult.successWrapedResult(this.riskIndusRiskWarnService.getRiskindusriskwarncnt());
            return wrappedResult;
        } catch (Exception var4) {
            this.log.error("获取产业安全风险预警数失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取产业安全风险预警数失败!");
        }
    }
    @ApiOperation("水库、大坝、水电站情况统计数")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/srpRiskStationInfo"}
    )
    @LogAnnotation(module="水库、大坝、水电站情况统计数",operator="查询",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/产业安全风险")
    public WrappedResult getSrpriskstationinfo(HttpServletRequest request) {

        try {
            return WrappedResult.successWrapedResult(this.riskIndusRiskWarnService.getSrpriskstationinfo());
        } catch (Exception var4) {
            this.log.error("获取水库、大坝、水电站情况统计数失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取水库、大坝、水电站情况统计数失败!");
        }
    }
    @ApiOperation("抽水蓄能电站洞室施工建设情况汇总")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/srpRiskHydroPowerStationInfo"}
    )
    @LogAnnotation(module="抽水蓄能电站洞室施工建设情况汇总",operator="查询",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/产业安全风险")
    public WrappedResult getRiskhydropowerstationinfo(HttpServletRequest request) {

      try {
            return WrappedResult.successWrapedResult(this.riskIndusRiskWarnService.getRiskhydropowerstationinfo());
        } catch (Exception var4) {
            this.log.error("获取抽水蓄能电站洞室施工建设情况汇总数失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取抽水蓄能电站洞室施工建设情况汇总数失败!");
        }
    }

    @ApiOperation("生物质料场情况汇总")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/srpRiskBiologicalMaterInfo"}
    )
    @LogAnnotation(module="生物质料场情况汇总",operator="查询",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/产业安全风险")
    public WrappedResult getRiskbiologicalmaterinfo(HttpServletRequest request) {

        try {
            return WrappedResult.successWrapedResult(this.riskIndusRiskWarnService.getRiskbiologicalmaterinfo());
        } catch (Exception var4) {
            this.log.error("获取生物质料场情况汇总失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取生物质料场情况汇总失败!");
        }
    }


    @ApiOperation("产业风险业预警单分页查询")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/risRiskIndusRiskWarnList"}
    )
    @LogAnnotation(module="产业风险业预警单分页查询",operator="查询",xtcs="risRiskIndusRiskWarnListNinePage",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/安全风险全景感知/产业安全风险")
    public WrappedResult getSrpriskindusriskwarnlist(HttpServletRequest request, @RequestBody String param) {
        try {
            WrappedResult wrappedResult= WrappedResult.successWrapedResult(this.riskIndusRiskWarnService.getSrpriskindusriskwarnlist(param));
            return wrappedResult;
        } catch (Exception var4) {
            this.log.error("获取产业风险业预警单分页查询失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取产业风险业预警单分页查询失败!");
        }
    }


}
