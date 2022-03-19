package com.raysdata.enterprisesadmittanceserver.controller;


import com.nariit.rmcp.common.vo.WrappedResult;
import com.raysdata.enterprisesadmittanceserver.servrce.examinationService;
import com.raysdata.enterprisesadmittanceserver.servrce.personnelInformationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping({"/examination"})
@Api("企业准入人员接口api")
public class examinationController {
    private static final String SESSION_TIMEOUT_TIP = "系统登录超时，请重新登录！";
    private final Logger log = LoggerFactory.getLogger(examinationController.class);

    @Autowired
    private examinationService examinationservice;

    //全网考试情况  考试次数  examination_number 考试人数  examination_person_number 考试通过人数  passing_number 考试通过率    probability
    @ApiOperation("全网考试情况")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getNetworkWide"}
    )
    public WrappedResult getNetworkWide(HttpServletRequest request) {
        try {
            return WrappedResult.successWrapedResult(this.examinationservice.getNetworkWide());
        } catch (Exception var4) {
            log.error("获取全网考试情况数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取全网考试情况数据失败!");
        }
    }


    //2.各单位考试情况 输入参数： 月份      Month 输出参数：省份id    column_type_code 省份名称  column_type_name 考试次数  examination_number 考试人数  examination_person_number
    //考试通过人数  passing_number 考试通过率    probability
    @ApiOperation("各单位考试情况")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getUnitTestData"}
    )
    public WrappedResult getUnitTestData(HttpServletRequest request, @RequestParam String Month) {
        try {
            return WrappedResult.successWrapedResult(this.examinationservice.getUnitTestData(Month));
        } catch (Exception var4) {
            log.error("获取各单位考试情况数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取各单位考试情况数据失败!");
        }
    }


    //省份小代码  输入参数：无    输出参数：省份id    column_type_code 省份名称  column_type_name
    @ApiOperation("省份小代码")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getDatareportOrg"}
    )
    public WrappedResult getDatareportOrg(HttpServletRequest request) {
        try {
            return WrappedResult.successWrapedResult(this.examinationservice.getDatareportOrg());
        } catch (Exception var4) {
            log.error("获取省份小代码数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取省份小代码数据失败!");
        }
    }

    //考试状态小代码  输入参数：无    输出参数：状态id    column_type_code 状态名称  column_type_name
    @ApiOperation("考试状态小代码")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getExaminationStatus"}
    )
    public WrappedResult getExaminationStatus(HttpServletRequest request) {
        try {
            return WrappedResult.successWrapedResult(this.examinationservice.getExaminationStatus());
        } catch (Exception var4) {
            log.error("获取考试状态小代码数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取考试状态小代码数据失败!");
        }
    }

    //考场  输入参数：无    输出参数：考场名称 testroom_name, 考试编号 testroom_no
    @ApiOperation("考场名称小代码")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getTestroomName"}
    )
    public WrappedResult getTestroomName(HttpServletRequest request) {
        try {
            return WrappedResult.successWrapedResult(this.examinationservice.getTestroomName());
        } catch (Exception var4) {
            log.error("获取考场名称小代码数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取考场名称小代码数据失败!");
        }
    }

    //查询条件：省份  datareport_org_id，考场编号 testroom_no，考试名称 test_name，考试状态 test_state，开始时间 starttime，结束时间 begintime
    //查询结果：省份  datareport_org_id，考场 testroom_name，考场编号 testroom_no，考试名称 test_name，考试人数 number，开始日期 starttime，结束日期 begintime，考题状态 test_state
    @ApiOperation("查询考场列表")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getExaminationList"}
    )
    public WrappedResult getExaminationList(HttpServletRequest request,
                                            @RequestParam String Page,
                                            @RequestParam String Number,
                                            @RequestParam String datareport_org_id,
                                            @RequestParam String testroom_no,
                                            @RequestParam String test_name,
                                            @RequestParam String test_state,
                                            @RequestParam String starttime,
                                            @RequestParam String begintime
    ) {
        try {
            return WrappedResult.successWrapedResult(this.examinationservice.getExaminationList(Page, Number, datareport_org_id, testroom_no, test_name, test_state, starttime, begintime));
        } catch (Exception var4) {
            log.error("获取工作负责人,外包人员数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取工作负责人,外包人员数据失败!");
        }
    }
    //删除企业列表数据
    @ApiOperation("删除安全考场列表数据")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/deleteExamination"}
    )
    public WrappedResult deleteInfo(HttpServletRequest request,@RequestBody String str) {

        try {
            return WrappedResult.successWrapedResult(this.examinationservice.deleteExamination(str));
        } catch (Exception var4) {
            log.error("删除列表数据失败 ", var4);
            return WrappedResult.failedWrappedResult("删除列表数据失败!");
        }
    }

    //5.考试详情基本信息  输入参数  考场编码 testroom_no
    @ApiOperation("各单位考试情况")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getEssentialInformation"}
    )
    public WrappedResult getEssentialInformation(HttpServletRequest request, @RequestParam String testroom_no) {
        try {
            return WrappedResult.successWrapedResult(this.examinationservice.getEssentialInformation(testroom_no));
        } catch (Exception var4) {
            log.error("获取各单位考试情况数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取各单位考试情况数据失败!");
        }
    }

    //5.考试详情基本信息  输入参数  考场编码 testroom_no
    @ApiOperation("人员列表")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getPersonnelList"}
    )
    public WrappedResult getPersonnelList(HttpServletRequest request, @RequestParam String Page,
                                          @RequestParam String Number,
                                          @RequestParam String testroom_no,
                                          @RequestParam String test_state,
                                          @RequestParam String starttime,
                                          @RequestParam String begintime) {
        try {
            return WrappedResult.successWrapedResult(this.examinationservice.getPersonnelList(Page, Number, testroom_no,test_state,starttime,begintime));
        } catch (Exception var4) {
            log.error("获取各单位考试情况数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取各单位考试情况数据失败!");
        }
    }

    @ApiOperation("省份联动考场数据")
    @RequestMapping(
        method = {RequestMethod.GET},
            value = {"/getProvincialexamination"}
    )
    public WrappedResult getProvincialexamination(HttpServletRequest request,@RequestParam String datareport_org_id){
        try{
            return WrappedResult.successWrapedResult(this.examinationservice.getProvincialexamination(datareport_org_id));
        }catch (Exception var4){
            log.error("获取省份联动考场数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取省份联动考场数据失败!");
        }
    }

}
