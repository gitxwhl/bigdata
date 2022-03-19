package com.raysdata.riskmanagementserver.controller;

import com.nariit.rmcp.common.vo.WrappedResult;
import com.raysdata.riskmanagementserver.aop.LogAnnotation;
import com.raysdata.riskmanagementserver.service.RiskWorkWarnForAllService;
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
@RequestMapping({"/riskWorkWarnForAll"})
@Api("风险作业全景化展示接口api")
@CrossOrigin
public class RiskWorkWarnForAllControllerTest {

    private static final String SESSION_TIMEOUT_TIP = "系统登录超时，请重新登录！";
    private final Logger log = LoggerFactory.getLogger(RiskWorkWarnForAllControllerTest.class);
    @Autowired
    private RiskWorkWarnForAllService riskWorkWarnForAllService;
    @ApiOperation("地图各单风险作业预警情况")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/srpRiskWorkWarnForAllAreaCNT"}
    )
    @LogAnnotation(module="地图各单风险作业预警情况",operator="查询",xtcs="riskWarnAreaCNTFive",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/风险作业统计分析/风险作业全景化展示")
    public WrappedResult getSrpriskworkwarnforallareacnt(HttpServletRequest request, @RequestBody String paramJson) {

        try {
            return WrappedResult.successWrapedResult(this.riskWorkWarnForAllService.getSrpriskworkwarnforallareacnt(paramJson));
        } catch (Exception var4) {
            this.log.error("获取地图各单风险作业预警情况失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取地图各单风险作业预警情况失败!");
        }
    }

    @ApiOperation("作业类型风险数量统计")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/srpRiskWorkWarnForAllWorkTypeCNT"}
    )
    @LogAnnotation(module="作业类型风险数量统计",operator="查询",xtcs="srpRiskWorkWarnForAllWorkTypeCNTOne",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/风险作业统计分析/风险作业全景化展示")
    public WrappedResult getSrpriskworkwarnforallworktypecnt(HttpServletRequest request, @RequestParam String areaId) {

        try {
            return WrappedResult.successWrapedResult(this.riskWorkWarnForAllService.getSrpriskworkwarnforallworktypecnt(areaId));
        } catch (Exception var4) {
            this.log.error("获取作业类型风险数量统计失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取作业类型风险数量统计失败!");
        }
    }

    @ApiOperation("风险作业状态数量统计")
    @RequestMapping(
            method = {RequestMethod.POST},
                value = {"/srpRiskWorkWarnWorkStateCNT"}
    )
    @LogAnnotation(module="风险作业状态数量统计",operator="查询",xtcs="srpRiskWorkWarnWorkStateCNTOne",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/风险作业统计分析/风险作业全景化展示")
    public WrappedResult getSrpriskworkwarnworkstatecnt(HttpServletRequest request, @RequestParam String areaId) {
        try {
            return WrappedResult.successWrapedResult(this.riskWorkWarnForAllService.getSrpriskworkwarnworkstatecnt(areaId));
        } catch (Exception var4) {
            this.log.error("获取风险作业状态数量统计失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取风险作业状态数量统计失败!");
        }
    }


    @ApiOperation("各省公司作业风险数量统计和各省公司作业电网风险数量统计")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/srpRiskWorkWarnAreaCNT"}
    )
    @LogAnnotation(module="各省公司作业风险数量统计和各省公司作业电网风险数量统计",operator="查询",xtcs="srpRiskWorkWarnAreaCNTTwo",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/风险作业统计分析/风险作业全景化展示")
    public WrappedResult getSrpriskworkwarnareacnt(HttpServletRequest request, @RequestBody String paramJson) {
        try {
            return WrappedResult.successWrapedResult(this.riskWorkWarnForAllService.getSrpriskworkwarnareacnt(paramJson));
        } catch (Exception var4) {
            this.log.error("获取各省公司作业风险数量统计和各省公司作业电网风险数量统计失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取各省公司作业风险数量统计和各省公司作业电网风险数量统计失败!");
        }
    }
    @ApiOperation("地市作业计划详情列表")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/srpRiskGridConstWorkList"}
    )
    @LogAnnotation(module="地市作业计划详情列表",operator="查询",xtcs="srpRiskGridConstWorkListThreePage",AllXtcs = "安全生产风险管控平台/安全风险全景感知模块/风险作业统计分析/风险作业全景化展示")
    public WrappedResult getSrpriskgridconstworklist(HttpServletRequest request, @RequestBody String paramJson) {
        try {
            return WrappedResult.successWrapedResult(this.riskWorkWarnForAllService.getSrpriskgridconstworklist(paramJson));
        } catch (Exception var4) {
            this.log.error("获取地市作业计划详情列表失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取地市作业计划详情列表失败!");
        }
    }
    @ApiOperation("作业计划详情查询")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/srpRiskWorkPlanInfo"}
    )
    @LogAnnotation(module="作业计划详情查询",operator="查询",xtcs="srpRiskWorkPlanInfoOne")
    public WrappedResult getSrpriskworkplaninfo(HttpServletRequest request, @RequestParam String workPlanId) {
        try {
            return WrappedResult.successWrapedResult(this.riskWorkWarnForAllService.getSrpriskworkplaninfo(workPlanId));
        } catch (Exception var4) {
            this.log.error("获取作业计划详情查询失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取作业计划详情查询失败!");
        }
    }

//    @SneakyThrows
//    @ApiOperation("视频下载")
//    @RequestMapping(
//            method = {RequestMethod.GET},
//            value = {"/httpDownload"}
//    )
//    public WrappedResult httpDownload(String httpUrl,String saveFile){
//       WrappedResult wrappedResult = WrappedResult.successWrapedResult(this.riskWorkWarnForAllService.httpDownload(httpUrl, saveFile));
//       Boolean flag= wrappedResult.isSuccessful();
//
//       if(flag){
//          return wrappedResult;
//        }
//        return WrappedResult.failedWrappedResult("获取视频失败!");
//        /*try {
//            return WrappedResult.successWrapedResult(this.riskWorkWarnForAllService.httpDownload(httpUrl, saveFile));
//        } catch (Exception var4) {
//            this.log.error("获取视频失败 :{}", var4.getMessage());
//            return WrappedResult.failedWrappedResult("获取视频失败!");
//        }*/
//    }
//    @Test
//    public void httpDownloadsss() {
//        httpDownload("https://vd3.bdstatic.com/mda-kkena9xzt4hmb7b7/sc/cae_h264_clips/1605430730/mda-kkena9xzt4hmb7b7.mp4?auth_key=1611666983-0-0-d415de02c6e1d9e81fe664f3cf8d4ebe&bcevod_channel=searchbox_feed&pd=1&pt=3&abtest","D:\\小赵课程\\test/22.mp4");
//    }


}
