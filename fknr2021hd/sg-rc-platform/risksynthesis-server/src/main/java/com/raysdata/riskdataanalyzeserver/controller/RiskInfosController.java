package com.raysdata.riskdataanalyzeserver.controller;


import com.nariit.rmcp.common.vo.WrappedResult;
import com.raysdata.riskdataanalyzeserver.service.RiskInfosService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping({"/riskInfos"})
@Api("电网运行风险综合情况接口api")
public class RiskInfosController {

    private static final String SESSION_TIMEOUT_TIP = "系统登录超时，请重新登录！";
    private final Logger log = LoggerFactory.getLogger(RiskInfosController.class);

    @Autowired
    RiskInfosService riskInfosService;

    //全网电网风险情况数据汇总
    @ApiOperation("全网电网风险情况数据汇总")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/gridRiskInfosCnt/{timeType}"}
    )
    public WrappedResult gridRiskInfosCnt(HttpServletRequest request, @PathVariable Integer timeType) {
//        String userId = ParamsUtil.getUserId(request);
//        if (StringUtils.isEmpty(userId)) {
//            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
//        } else {
        try {
            return WrappedResult.successWrapedResult(this.riskInfosService.gridRiskInfosCnt(timeType));
        } catch (Exception var4) {
            log.error("获取全网电网风险情况数据汇总数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获全网电网风险情况数据汇总数据失败!");
        }
//        }
    }

    //各单位风险信息统计情况
    @ApiOperation("各单位风险信息统计情况")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/areaRiskCountInfo/{timeType}"}
    )
    public WrappedResult areaRiskCountInfo(HttpServletRequest request, @PathVariable Integer timeType) {
//        String userId = ParamsUtil.getUserId(request);
//        if (StringUtils.isEmpty(userId)) {
//            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
//        } else {
        try {
            return WrappedResult.successWrapedResult(this.riskInfosService.areaRiskCountInfo(timeType));
        } catch (Exception var4) {
            log.error("获取各单位风险信息统计情况数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取各单位风险信息统计情况数据失败!");
        }
//        }
    }

    //各单位电网风险分布情况
    @ApiOperation("各单位电网风险分布情况")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/gridRiskInfosCommonCnt"}
    )
    public WrappedResult gridRiskInfosCommonCnt(HttpServletRequest request, @RequestParam Integer infoType, @RequestParam Integer timeType, @RequestParam String beginTime, @RequestParam String endTime) {
//        String userId = ParamsUtil.getUserId(request);
//        if (StringUtils.isEmpty(userId)) {
//            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
//        } else {
        try {
            return WrappedResult.successWrapedResult(this.riskInfosService.gridRiskInfosCommonCnt(infoType, timeType, beginTime, endTime));
        } catch (Exception var4) {
            log.error("获取各单位风险信息统计情况数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取各单位风险信息统计情况数据失败!");
        }
//        }
    }

    //全网今日电网风险信息列表,分页查询
    @ApiOperation("全网今日电网风险信息列表")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/allRiskWarnInfo"}
    )
    public WrappedResult allRiskWarnInfo(HttpServletRequest request, @RequestBody String jsonStr) {
//        String userId = ParamsUtil.getUserId(request);
//        if (StringUtils.isEmpty(userId)) {
//            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
//        } else {
        try {
            return WrappedResult.successWrapedResult(this.riskInfosService.allRiskWarnInfo(jsonStr));
        } catch (Exception var4) {
            log.error("获取全网今日电网风险信息列表数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取全网今日电网风险信息列表数据失败!");
        }
//        }
    }


    //电网风险列表信息,分页查询
    @ApiOperation("电网风险列表信息")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/allRiskWarnInfosList"}
    )
    public WrappedResult allRiskWarnInfosList(HttpServletRequest request, @RequestBody String jsonStr) {
//        String userId = ParamsUtil.getUserId(request);
//        if (StringUtils.isEmpty(userId)) {
//            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
//        } else {
        try {
            return WrappedResult.successWrapedResult(this.riskInfosService.allRiskWarnInfosList(jsonStr));
        } catch (Exception var4) {
            log.error("获取全网今日电网风险信息列表数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取全网今日电网风险信息列表数据失败!");
        }
//        }
    }

    //电网风险详情
    @ApiOperation("电网风险详情")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/gridWarnContent"}
    )
    public WrappedResult gridWarnContent(HttpServletRequest request, @RequestParam String str) {
//        String userId = ParamsUtil.getUserId(request);
//        if (StringUtils.isEmpty(userId)) {
//            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
//        } else {
        try {
            return WrappedResult.successWrapedResult(this.riskInfosService.gridWarnContent(str));
        } catch (Exception var4) {
            log.error("获取电网风险详情数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取电网风险详情数据失败!");
        }
//        }
    }


    //电网风险详情
    @ApiOperation("获取字典定义数据")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getSrpRiskSysTab"}
    )
    public WrappedResult getSrpRiskSysTab(HttpServletRequest request) {
//        String userId = ParamsUtil.getUserId(request);
//        if (StringUtils.isEmpty(userId)) {
//            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
//        } else {
        try {
            return WrappedResult.successWrapedResult(this.riskInfosService.getSrpRiskSysTab());
        } catch (Exception var4) {
            log.error("获取字典定义数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取字典定义数据失败!");
        }
//        }
    }

    //电网风险详情
    @ApiOperation("删除列表数据")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/deleteInfo"}
    )
    public WrappedResult deleteInfo(HttpServletRequest request,@RequestBody String str) {

        try {
            return WrappedResult.successWrapedResult(this.riskInfosService.deleteInfo(str));
        } catch (Exception var4) {
            log.error("删除列表数据失败 ", var4);
            return WrappedResult.failedWrappedResult("删除列表数据失败!");
        }
    }

}
