package com.raysdata.riskdataanalyzeserver.controller;


import com.nariit.rmcp.common.vo.WrappedResult;
import com.raysdata.riskdataanalyzeserver.aop.LogAnnotation;
import com.raysdata.riskdataanalyzeserver.service.RiskDataInfosService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
@CrossOrigin
@RestController
@RequestMapping({"/riskDataInfosController"})
@Api("企业准入人员接口api")
public class RiskDataInfosController {

    private static final String SESSION_TIMEOUT_TIP = "系统登录超时，请重新登录！";
    private final Logger log = LoggerFactory.getLogger(RiskDataInfosController.class);

    @Autowired
    private RiskDataInfosService riskDataInfosService;

    @ApiOperation("数据治理")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/manageList"}
    )
    @LogAnnotation(module="数据治理",operator="查询",xtcs="sixPage",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/数据整合/数据治理")
    public WrappedResult getManageList(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.riskDataInfosService.getDataInfoManage(param));
        } catch (Exception var4) {
            this.log.error("获取数据整治表表失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取数据整治表失败!");
        }
    }

    @ApiOperation("数据清洗")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/purgeList"}
    )
    @LogAnnotation(module="数据清洗",operator="查询",xtcs="sixPage",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/数据整合/数据清洗")
    public WrappedResult getPurgeList(HttpServletRequest request, @RequestBody String param) {

        try {
            return WrappedResult.successWrapedResult(this.riskDataInfosService.getDataInfoPurge(param));
        } catch (Exception var4) {
            this.log.error("获取数据清洗表失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取数据清洗表失败!");
        }
    }
    @ApiOperation("数据加载")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/dataLoad"}
    )
    @LogAnnotation(module="数据加载",operator="查询",xtcs="sixPage",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/数据整合/数据加载")
    public WrappedResult getDataLoadList(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.riskDataInfosService.getDataInfoLoadList(param));
        } catch (Exception var4) {
            this.log.error("获取数据加载信息失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取数据加载信息失败!");
        }
    }
    @ApiOperation("数据转换")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/dataConversion"}
    )
    @LogAnnotation(module="数据转换",operator="查询",xtcs="sixPage",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/数据整合/数据转换")
    public WrappedResult getDataConversionList(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.riskDataInfosService.getDataInfoConversionList(request,param));
        } catch (Exception var4) {
            this.log.error("获取数据转换信息失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取数据转换信息失败!");
        }
    }
}
