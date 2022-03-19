package com.raysdata.riskdataanalyzeserver.controller;

import com.nariit.rmcp.common.vo.WrappedResult;
import com.raysdata.riskdataanalyzeserver.aop.LogAnnotation;
import com.raysdata.riskdataanalyzeserver.service.RiskWorkStaffInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
@CrossOrigin
@RestController
@RequestMapping({"/personnelInformation"})
@Api("企业准入人员接口api")
public class RiskWorkStaffInfoController {


    private static final String SESSION_TIMEOUT_TIP = "系统登录超时，请重新登录！";
    private final Logger log = LoggerFactory.getLogger(RiskWorkStaffInfoController.class);

    @Autowired
    private RiskWorkStaffInfoService riskWorkStaffInfoService;

    @ApiOperation("人员基本情况列表查询")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/srpRiskWorkerInfosList"}
    )
    @LogAnnotation(module="人员基本情况列表查询",operator="查询",xtcs="fivePage",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网人员安全能力分析/人员基本情况")
    public WrappedResult getRiskWorkerInfosList(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.riskWorkStaffInfoService.getRiskWorkerInfosList(param));
        } catch (Exception var4) {
            this.log.error("获取人员基本情况列表失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取人员基本情况列表失败!");
        }
    }

    @ApiOperation("人员项目列表查询")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/srpRiskWorkertoProjectList"}
    )
    @LogAnnotation(module="人员项目列表查询",operator="查询",xtcs="threePage",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网人员安全能力分析/人员基本情况")
    public WrappedResult getRiskWorkertoProjectList(HttpServletRequest request, @RequestBody String param) {

        try {
            return WrappedResult.successWrapedResult(this.riskWorkStaffInfoService.getRiskWorkertoProjectList(param));
        } catch (Exception var4) {
            this.log.error("获取人员项目列表失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取人员项目列表失败!");
        }
        //        }
    }


    @ApiOperation("人员信息统计")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/getStaffCount"}
    )
    @LogAnnotation(module="人员信息统计",operator="查询",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网人员安全能力分析/人员基本情况")
    public WrappedResult getStaffCount(HttpServletRequest request) {

        try {
            return WrappedResult.successWrapedResult(this.riskWorkStaffInfoService.getStaffCount());
        } catch (Exception var4) {
            this.log.error("人员信息统计失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("人员信息统计失败!");
        }
    }



    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @ApiOperation("人员全网信息联动列表查询")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/srpRiskWorkerLinkageInfosList"}
    )
    @LogAnnotation(module="人员全网信息联动列表查询",operator="查询",xtcs="fivePage",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网人员安全能力分析/人员全网信息联动")
    public WrappedResult getRiskWorkerLinkageInfosList(HttpServletRequest request, @RequestBody String param) {

        try {
            return WrappedResult.successWrapedResult(this.riskWorkStaffInfoService.getRiskWorkerLinkageInfosList(param));
        } catch (Exception var4) {
            this.log.error("获取人员全网信息联动列表查询失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取人员全网信息联动列表查询失败!");
        }
    }


    @ApiOperation("准入情况列表查询")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/getRiskWorkerAccessList"}
    )
    @LogAnnotation(module="准入情况列表查询",operator="查询",xtcs="threePage",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网人员安全能力分析/人员全网信息联动")
    public WrappedResult getRiskWorkerAccessList(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.riskWorkStaffInfoService.getRiskWorkerAccessList(param));
        } catch (Exception var4) {
            this.log.error("准入情况列表查询失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("准入情况列表查询失败!");
        }
    }


    @ApiOperation("人员单位变更记录列表查询")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/srpRiskWorkerUnitChangeList"}
    )
    @LogAnnotation(module="人员单位变更记录列表查询",operator="查询",xtcs="threePage",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网人员安全能力分析/人员全网信息联动")
    public WrappedResult getRiskWorkerUnitChangeList(HttpServletRequest request, @RequestBody String param) {

        try {
            return WrappedResult.successWrapedResult(this.riskWorkStaffInfoService.getRiskWorkerUnitChangeList(param));
        } catch (Exception var4) {
            this.log.error("获取人员单位变更记录列表查询失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取人员单位变更记录列表查询失败!");
        }
    }



    @ApiOperation("人员详情查询")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/getWorkerInfo"}
    )
    @LogAnnotation(module="人员单位变更记录列表查询",operator="查询",xtcs="one",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网人员安全能力分析/人员全网信息联动")
    public WrappedResult getWorkerInfo(HttpServletRequest request, @RequestBody String param) {

        try {
            return WrappedResult.successWrapedResult(this.riskWorkStaffInfoService.getWorkerInfo(param));
        } catch (Exception var4) {
            this.log.error("人员详情查询失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("人员详情查询失败!");
        }
    }


    @ApiOperation("人员详情查询")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/getWorkerInfoTwo"}
    )
    @LogAnnotation(module="人员单位变更记录列表查询",operator="查询",xtcs="one",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网人员安全能力分析/人员基本情况")
    public WrappedResult getWorkerInfoTwo(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.riskWorkStaffInfoService.getWorkerInfo(param));
        } catch (Exception var4) {
            this.log.error("人员详情查询失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("人员详情查询失败!");
        }
    }



    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @ApiOperation("人员安全资信列表查询")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/srpRiskWorkerSafeInfoList"}
    )
    @LogAnnotation(module="人员安全资信列表查询",operator="查询",xtcs="fivePage",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网人员安全能力分析/人员安全资信")
    public WrappedResult getRiskWorkerSafeInfoList(HttpServletRequest request, @RequestBody String param) {

        try {
            return WrappedResult.successWrapedResult(this.riskWorkStaffInfoService.getRiskWorkerSafeInfoList(param));
        } catch (Exception var4) {
            this.log.error("获取人员安全资信列表查询失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取人员安全资信列表查询失败!");
        }
    }

    @ApiOperation("人员安全资信统计")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/getSafeCount"}
    )
    @LogAnnotation(module="人员安全资信统计",operator="查询",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网人员安全能力分析/人员安全资信")
    public WrappedResult getSafeCount(HttpServletRequest request) {

        try {
            return WrappedResult.successWrapedResult(this.riskWorkStaffInfoService.getSafeCount());
        } catch (Exception var4) {
            this.log.error("人员安全资信统计失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("人员安全资信统计失败!");
        }
    }


    @ApiOperation("人员培训记录列表查询")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/srpRiskWorkerSafeLearnList"}
    )
    @LogAnnotation(module="人员培训记录列表查询",operator="查询",xtcs="threePage")
    public WrappedResult getRiskWorkerSafeLearnList(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.riskWorkStaffInfoService.getRiskWorkerSafeLearnList(param));
        } catch (Exception var4) {
            this.log.error("获取人员培训记录列表查询失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取人员培训记录列表查询失败!");
        }
        //        }
    }


    @ApiOperation("人员考试记录列表查询")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/srpRiskWorkerSafetyTestList"}
    )
    @LogAnnotation(module="人员培训记录列表查询",operator="查询",xtcs="threePage")
    public WrappedResult getRiskWorkerSafetyTestList(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.riskWorkStaffInfoService.getRiskWorkerSafetyTestList(param));
        } catch (Exception var4) {
            this.log.error("获取人员考试记录列表查询失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取人员考试记录列表查询失败!");
        }
    }

    @ApiOperation("人员违章记录列表查询")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/srpRiskWorkerViolationPointsList"}
    )
    @LogAnnotation(module="人员培训记录列表查询",operator="查询",xtcs="threePage",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/全网人员安全能力分析/人员全网信息联动")
    public WrappedResult getRiskWorkerViolationPointsList(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.riskWorkStaffInfoService.getRiskWorkerViolationPointsList(param));
        } catch (Exception var4) {
            this.log.error("获取人员违章记录列表查询失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取人员违章记录列表查询失败!");
        }
    }


}
