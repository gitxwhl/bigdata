package com.raysdata.riskdataanalyzeserver.controller;

import com.nariit.rmcp.common.vo.WrappedResult;
import com.raysdata.riskdataanalyzeserver.aop.LogAnnotation;
import com.raysdata.riskdataanalyzeserver.service.ToolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
@CrossOrigin
@RestController
@RequestMapping({"/toolInformation"})
@Api("企业准入人员接口api")
public class ToolController {
    private static final String SESSION_TIMEOUT_TIP = "系统登录超时，请重新登录！";
    private final Logger log = LoggerFactory.getLogger(ToolController.class);

    @Autowired
    ToolService toolService;

    @ApiOperation("数据统计")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/dataStatistics"}
    )
    @LogAnnotation(module="数据统计",operator="查询",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网安全工器具预警分析/安全工器具数据统计")
    public WrappedResult dataStatistics(HttpServletRequest request) {
        try {
            return WrappedResult.successWrapedResult(this.toolService.dataStatistics());
        } catch (Exception var4) {
            this.log.error("获取数据统计失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取数据统计失败!");
        }
    }

    @ApiOperation("实时预警")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/realWarning"}
    )
    @LogAnnotation(module="实时预警",operator="查询",xtcs="sevenPage",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网安全工器具预警分析/安全工器具实时预警")
    public WrappedResult realWarning(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.toolService.realWarning(param));
        } catch (Exception var4) {
            this.log.error("获取实时预警失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取实时预警失败!");
        }
    }


    @ApiOperation("供应商评价")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/getGradeList"}
    )
    @LogAnnotation(module="供应商评价",operator="查询",xtcs="forPage",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网安全工器具预警分析/安全工器具供应商评价")
    public WrappedResult getGradeList(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.toolService.getGradeList(param));
        } catch (Exception var4) {
            this.log.error("获取供应商评价失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取供应商评价失败!");
        }
    }

}
