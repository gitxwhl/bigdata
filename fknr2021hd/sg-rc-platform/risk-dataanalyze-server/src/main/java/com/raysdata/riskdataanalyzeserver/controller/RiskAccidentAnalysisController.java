package com.raysdata.riskdataanalyzeserver.controller;


import com.nariit.rmcp.common.vo.WrappedResult;
import com.raysdata.riskdataanalyzeserver.aop.LogAnnotation;
import com.raysdata.riskdataanalyzeserver.service.RiskAccidentAnalysisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
@CrossOrigin
@RestController
@RequestMapping({"/riskAccidentAnalysis"})
@Api("企业准入人员接口api")
public class RiskAccidentAnalysisController {
    private static final String SESSION_TIMEOUT_TIP = "系统登录超时，请重新登录！";
    private final Logger log = LoggerFactory.getLogger(RiskAccidentAnalysisController.class);

    @Autowired
    private RiskAccidentAnalysisService riskAccidentAnalysisService;

    @ApiOperation("获取字典定义数据")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getSrpRiskSysTab"}
    )
    @LogAnnotation(module="获取字典定义数据",operator="查询",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网安全事故事件分析/分类统计")
    public WrappedResult getSrpRiskSysTab(HttpServletRequest request) {
        try {
            return WrappedResult.successWrapedResult(this.riskAccidentAnalysisService.getSrpRiskSysTab());
        } catch (Exception var4) {
            log.error("获取字典定义数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取字典定义数据失败!");
        }
    }

    @ApiOperation("设备事故列表查询")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/equipAccidentList"}
    )
    @LogAnnotation(module="设备事故列表查询",operator="查询",xtcs="eightPage",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网安全事故事件分析/多维度展现对比")
    public WrappedResult getEquipAccidentList(HttpServletRequest request, @RequestBody String param) {

        try {
            return WrappedResult.successWrapedResult(this.riskAccidentAnalysisService.getEquipAccidentList(param));
        } catch (Exception var4) {
            this.log.error("获取设备事故列表失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取设备事故列表失败!");
        }
    }

    @ApiOperation("设备事故列表查询")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/equipAccidentListTwo"}
    )
    @LogAnnotation(module="设备事故列表查询",operator="查询",xtcs="eightPage",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网安全事故事件分析/分类统计")
    public WrappedResult getEquipAccidentListTwo(HttpServletRequest request, @RequestBody String param) {

        try {
            return WrappedResult.successWrapedResult(this.riskAccidentAnalysisService.getEquipAccidentList(param));
        } catch (Exception var4) {
            this.log.error("获取设备事故列表失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取设备事故列表失败!");
        }
    }

    @ApiOperation("人身事故列表查询")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/personalAccidentList"}
    )
    @LogAnnotation(module="人身事故列表查询",operator="查询",xtcs="personalAccidentListSevenPage",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网安全事故事件分析/多维度展现对比")
    public WrappedResult getPersonalAccidentList(HttpServletRequest request, @RequestBody String param) {

        try {
            return WrappedResult.successWrapedResult(this.riskAccidentAnalysisService.getPersonalAccidentList(param));
        } catch (Exception var4) {
            this.log.error("获取人身事故列表失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取人身事故列表失败!");
        }
    }


    @ApiOperation("人身事故列表查询")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/personalAccidentListTwo"}
    )
    @LogAnnotation(module="人身事故列表查询",operator="查询",xtcs="personalAccidentListSevenPage",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网安全事故事件分析/分类统计")
    public WrappedResult getPersonalAccidentListTwo(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.riskAccidentAnalysisService.getPersonalAccidentList(param));
        } catch (Exception var4) {
            this.log.error("获取人身事故列表失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取人身事故列表失败!");
        }
    }

    @ApiOperation("电网事故列表查询")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/powerGridAccidentList"}
    )
    @LogAnnotation(module="电网事故列表查询",operator="查询",xtcs="powerGridAccidentListSevenPage",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网安全事故事件分析/多维度展现对比")
    public WrappedResult getPowerGridAccidentList(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.riskAccidentAnalysisService.getPowerGridAccidentList(param));
        } catch (Exception var4) {
            this.log.error("获取电网事故列表失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取电网事故列表失败!");
        }
    }


    @ApiOperation("电网事故列表查询")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/powerGridAccidentListTwo"}
    )
    @LogAnnotation(module="电网事故列表查询",operator="查询",xtcs="powerGridAccidentListSevenPage",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网安全事故事件分析/分类统计")
    public WrappedResult getPowerGridAccidentListTwo(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.riskAccidentAnalysisService.getPowerGridAccidentList(param));
        } catch (Exception var4) {
            this.log.error("获取电网事故列表失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取电网事故列表失败!");
        }
    }




    @ApiOperation("网络信息事故列表查询")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/informAccidentList"}
    )
    @LogAnnotation(module="网络信息事故列表查询",operator="查询",xtcs="informAccidentListSevenPage",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网安全事故事件分析/多维度展现对比")
    public WrappedResult getInformAccidentList(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.riskAccidentAnalysisService.getInformAccidentList(param));
        } catch (Exception var4) {
            this.log.error("获取网络信息事故列表失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取网络信息事故列表失败!");
        }
    }

    @ApiOperation("网络信息事故列表查询")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/informAccidentListTwo"}
    )
    @LogAnnotation(module="网络信息事故列表查询",operator="查询",xtcs="informAccidentListSevenPage",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网安全事故事件分析/分类统计")
    public WrappedResult getInformAccidentListTwo(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.riskAccidentAnalysisService.getInformAccidentList(param));
        } catch (Exception var4) {
            this.log.error("获取网络信息事故列表失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取网络信息事故列表失败!");
        }
    }







    @ApiOperation("安全事故分类统计")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/accidentStatistics"}
    )
    @LogAnnotation(module="安全事故分类统计",operator="查询",xtcs="accidentStatisticsOne",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网安全事故事件分析/分类统计")
    public WrappedResult accidentStatistics(HttpServletRequest request, @RequestBody String param) {

        try {
            return WrappedResult.successWrapedResult(this.riskAccidentAnalysisService.getAccidentStatistics(param));
        } catch (Exception var4) {
            this.log.error("获取安全事故分类统计失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取安全事故分类统计失败!");
        }
    }

    @ApiOperation("按类型同比统计")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/accidentCountByMonth"}
    )
    @LogAnnotation(module="按类型同比统计",operator="查询",xtcs="accidentCountByMonthOne",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网安全事故事件分析/多维度展现对比")
    public WrappedResult accidentCountByMonth(HttpServletRequest request, @RequestBody String param) {

        try {
            return WrappedResult.successWrapedResult(this.riskAccidentAnalysisService.getAccidentCountByMonth(param));
        } catch (Exception var4) {
            this.log.error("获取按类型同比统计统计失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取按类型同比统计统计失败!");
        }
    }

    @ApiOperation("按类型同比统计")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/accidentCountByMonthTwo"}
    )
    @LogAnnotation(module="按类型同比统计",operator="查询",xtcs="accidentCountByMonthOne",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网安全事故事件分析/分类统计")
    public WrappedResult accidentCountByMonthTwo(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.riskAccidentAnalysisService.getAccidentCountByMonth(param));
        } catch (Exception var4) {
            this.log.error("获取按类型同比统计统计失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取按类型同比统计统计失败!");
        }
    }

    @ApiOperation("按类型环比统计")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/accidentCount"}
    )
    @LogAnnotation(module="按类型环比统计",operator="查询",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网安全事故事件分析/多维度展现对比")
    public WrappedResult accidentCount(HttpServletRequest request) {
        try {
            return WrappedResult.successWrapedResult(this.riskAccidentAnalysisService.getAccidentCount());
        } catch (Exception var4) {
            this.log.error("获取按类型环比统计统计失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取按类型环比统计统计失败!");
        }
    }

    @ApiOperation("按类型环比统计")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/accidentCountTwo"}
    )
    @LogAnnotation(module="按类型环比统计",operator="查询",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网安全事故事件分析/分类统计")
    public WrappedResult accidentCountTwo(HttpServletRequest request) {
        try {
            return WrappedResult.successWrapedResult(this.riskAccidentAnalysisService.getAccidentCount());
        } catch (Exception var4) {
            this.log.error("获取按类型环比统计统计失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取按类型环比统计统计失败!");
        }
    }



    @ApiOperation("趋势预测分析")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/tendencyAnalyze"})
    @LogAnnotation(module="趋势预测分析",operator="查询",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网安全事故事件分析/趋势预测分析")
    public WrappedResult getTendencyAnalyze(HttpServletRequest request){
        try {
            return WrappedResult.successWrapedResult(this.riskAccidentAnalysisService.getTendencyAnalyze());
        } catch (Exception var) {
            this.log.error("获取趋势预测分析失败 :{}", var.getMessage());
            return WrappedResult.failedWrappedResult("获取趋势预测分析失败!");
        }

    }

    //------------------------------------------按等级统计-------------------------------------------------------------------

    @ApiOperation("按事件等级统计")
    @RequestMapping(method = {RequestMethod.POST}, value = {"/accidentGrade"})
    @LogAnnotation(module="按事件等级统计",operator="查询",xtcs="accidentGradeOne",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网安全事故事件分析/多维度展现对比")
    public WrappedResult getAccidentGrade(HttpServletRequest request, @RequestBody String param){
        try {
            return WrappedResult.successWrapedResult(this.riskAccidentAnalysisService.getAccidentGrade(param));
        } catch (Exception var) {
            this.log.error("获取事件等级失败 :{}", var.getMessage());
            return WrappedResult.failedWrappedResult("获取事件等级失败!");
        }

    }


    //------------------------------------------按单位统计-------------------------------------------------------------------
    @ApiOperation("按单位统计")
    @RequestMapping(method = {RequestMethod.POST}, value = {"/gridcrop"})
    @LogAnnotation(module="按单位统计",operator="查询",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网安全事故事件分析/多维度展现对比")
    public WrappedResult getEquipAccidentGridcrop(HttpServletRequest request){
        try {
            return WrappedResult.successWrapedResult(this.riskAccidentAnalysisService.getAccidentGridcrop());
        }catch (Exception var) {
            this.log.error("获取单位统计失败 :{}", var.getMessage());
            return WrappedResult.failedWrappedResult("获取单位统计失败!");
        }
    }

    //------------------------------------------按电压等级统计----------------------------------------------------------------
    @ApiOperation("按电压等级统计")
    @RequestMapping(method = {RequestMethod.POST}, value = {"/voltageLevel"})
    @LogAnnotation(module="按电压等级统计",operator="查询",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网安全事故事件分析/多维度展现对比")
    public WrappedResult getAccidentVoltageLevel(HttpServletRequest request){
        try {
            return WrappedResult.successWrapedResult(this.riskAccidentAnalysisService.getAccidentVoltageLevel());
        }catch (Exception var) {
            this.log.error("获取电压等级失败 :{}", var.getMessage());
            return WrappedResult.failedWrappedResult("获取电压等级失败!");
        }
    }

    //------------------------------------------按事故原因TOP10统计-----------------------------------------------------------
    @ApiOperation("按事故原因统计")
    @RequestMapping(method = {RequestMethod.POST}, value = {"/accidentDesc"})
    @LogAnnotation(module="按事故原因统计",operator="查询",xtcs="accidentDescOne",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网安全事故事件分析/多维度展现对比")
    public WrappedResult getAccidentDesc(HttpServletRequest request, @RequestBody String param){
        try {
            return WrappedResult.successWrapedResult(this.riskAccidentAnalysisService.getAccidentDesc(param));
        } catch (Exception var) {
            this.log.error("获取事故原因失败 :{}", var.getMessage());
            return WrappedResult.failedWrappedResult("获取事故原因失败!");
        }

    }




    @ApiOperation("事件数量统计")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/accidentStatistics1"}
    )
    @LogAnnotation(module="事件数量统计",operator="查询",xtcs="accidentStatistics1One",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网安全事故事件分析/多维度展现对比")
    public WrappedResult accidentStatistics1(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.riskAccidentAnalysisService.getAccidentStatistics1(param));
        } catch (Exception var4) {
            this.log.error("获取事件数量统计失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取事件数量统计失败!");
        }
    }


}
