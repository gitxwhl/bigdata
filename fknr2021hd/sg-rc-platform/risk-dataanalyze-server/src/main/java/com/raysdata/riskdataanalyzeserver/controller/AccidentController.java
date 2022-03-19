package com.raysdata.riskdataanalyzeserver.controller;

import com.nariit.rmcp.common.vo.WrappedResult;
import com.raysdata.riskdataanalyzeserver.aop.LogAnnotation;
import com.raysdata.riskdataanalyzeserver.service.AccidentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping({"/accidentInformation"})
@Api("企业准入人员接口api")
public class AccidentController {
    private static final String SESSION_TIMEOUT_TIP = "系统登录超时，请重新登录！";
    private final Logger log = LoggerFactory.getLogger(AccidentController.class);

    @Autowired
    private AccidentService accidentService;

    @ApiOperation("外包单位风险画像列表")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/getRiskPortrait"}
    )
    @LogAnnotation(module="外包单位风险画像列表",operator="查询",xtcs="getRiskPortraitforePage",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/外包单位作业风险画像")
    public WrappedResult getRiskPortrait(HttpServletRequest request, @RequestBody String param) {
        try {
            WrappedResult wrappedResult= WrappedResult.successWrapedResult(this.accidentService.getRiskPortrait(param));
            return wrappedResult;
            } catch (Exception var4) {
            this.log.error("获取外包单位风险画像列表失败 :{}", var4.getMessage());
                return WrappedResult.failedWrappedResult("获取外包单位风险画像列表失败!");
            }
    }


    @ApiOperation("单位基本情况")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/basicInformation"}
    )
    @LogAnnotation(module="单位基本情况",operator="查询",xtcs="basicInformationThreePage",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/外包单位作业风险画像")
    public WrappedResult getBasicInformation(HttpServletRequest request, @RequestBody String param) {

        try {
            WrappedResult wrappedResult= WrappedResult.successWrapedResult(this.accidentService.basicInformation(param));
            return wrappedResult;
        } catch (Exception var4) {
            this.log.error("获取单位基本情况失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取单位基本情况失败!");
        }
    }
    @ApiOperation("单位安全资信")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/securityInformation"}
    )
    @LogAnnotation(module="单位安全资信",operator="查询",xtcs="wbfxhxOne",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/外包单位作业风险画像")
    public WrappedResult getSecurityInformation(HttpServletRequest request, @RequestBody String param) {

        try {
            return WrappedResult.successWrapedResult(this.accidentService.securityInformation(param));
        } catch (Exception var4) {
            this.log.error("获取单位安全资信失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取单位安全资信失败!");
        }
    }

    @ApiOperation("全网信息联动")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/networkInformation"}
    )
    @LogAnnotation(module="全网信息联动",operator="查询",xtcs="wbfxhxOne",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/外包单位作业风险画像")
    public WrappedResult getNetworkInformation(HttpServletRequest request, @RequestBody String param) {

        try {
            return WrappedResult.successWrapedResult(this.accidentService.networkInformation(param));
        } catch (Exception var4) {
            this.log.error("获取全网信息联动失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取全网信息联动失败!");
        }
    }


    @ApiOperation("安全能力分析")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/safetyAnalysis"}
    )
    @LogAnnotation(module="安全能力分析",operator="查询",xtcs="wbfxhxOne",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/外包单位作业风险画像")
    public WrappedResult getSafetyAnalysis(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.accidentService.safetyAnalysis(param));
        } catch (Exception var4) {
            this.log.error("获取安全能力分析失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取安全能力分析失败!");
        }
    }
}
