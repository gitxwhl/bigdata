package com.raysdata.enterprisesadmittanceserver.controller;


import com.nariit.rmcp.common.vo.WrappedResult;
import com.raysdata.enterprisesadmittanceserver.servrce.EnterprisesadmittanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping({"/enterprisesAdmittance"})
@Api("企业准入接口api")
public class EnterprisesadmittanceController {
    private static final String SESSION_TIMEOUT_TIP = "系统登录超时，请重新登录！";
    private final Logger log = LoggerFactory.getLogger(EnterprisesadmittanceController.class);

    @Autowired
    private EnterprisesadmittanceService enterprisesadmittanceService;



    @ApiOperation("企业模板导出")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/downloadEnterExcel"}
    )
    public WrappedResult downloadEnterExcel(HttpServletRequest request, HttpServletResponse response) {
        try {
            return WrappedResult.successWrapedResult(this.enterprisesadmittanceService.downloadEnterExcel(request,response));
        } catch (Exception var4) {
            this.log.error("模板文件导出失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("模板文件导出失败!");
        }
    }


    //全网准入单位情况汇总
    @ApiOperation("全网准入单位情况汇总")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getEnterpriseCnts"}
    )
    public WrappedResult getEnterpriseCnts(HttpServletRequest request) {

        try {
            return WrappedResult.successWrapedResult(this.enterprisesadmittanceService.getEnterpriseCnts());
        } catch (Exception var4) {
            log.error("获取全网准入单位情况汇总数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取全网准入单位情况汇总数据失败!");
        }
    }

    //各单位准入情况
    @ApiOperation("各单位准入情况")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getEnterprisesAdmittanceInfoCnt/{timeType}"}
    )
    public WrappedResult getEnterprisesAdmittanceInfoCnt(HttpServletRequest request, @PathVariable Integer timeType) {
//        String userId = ParamsUtil.getUserId(request);
//        if (StringUtils.isEmpty(userId)) {
//            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
//        } else {
        try {
            return WrappedResult.successWrapedResult(this.enterprisesadmittanceService.getEnterprisesAdmittanceInfoCnt(timeType));
        } catch (Exception var4) {
            log.error("获取各单位准入情况数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取各单位准入情况数据失败!");
        }
//        }
    }

    //企业信息查询列表
    @ApiOperation("企业信息查询列表")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/getWorkSiteinfosList"}
    )
    public WrappedResult getWorkSiteinfosList(HttpServletRequest request, @RequestBody String jsonStr) {

        try {
            return WrappedResult.successWrapedResult(this.enterprisesadmittanceService.getWorkSiteinfosList(jsonStr));
        } catch (Exception var4) {
            log.error("获取企业信息查询列表数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取企业信息查询列表数据失败!");
        }

    }
    //删除企业列表数据
    @ApiOperation("删除企业列表数据")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/deleteEnterprise"}
    )
    public WrappedResult deleteInfo(HttpServletRequest request,@RequestBody String str) {

        try {
            return WrappedResult.successWrapedResult(this.enterprisesadmittanceService.deleteEnterprise(str));
        } catch (Exception var4) {
            log.error("删除列表数据失败 ", var4);
            return WrappedResult.failedWrappedResult("删除列表数据失败!");
        }
    }

    //现场作业单位详细信息查询
    @ApiOperation("现场作业单位详细信息查询")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getSrpWorkSiteinfo/{siteinfoId}"}
    )
    public WrappedResult getSrpWorkSiteinfo(HttpServletRequest request, @PathVariable String siteinfoId) {
//        String userId = ParamsUtil.getUserId(request);
//        if (StringUtils.isEmpty(userId)) {
//            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
//        } else {
        try {
            return WrappedResult.successWrapedResult(this.enterprisesadmittanceService.getSrpWorkSiteinfo(siteinfoId));
        } catch (Exception var4) {
            log.error("获取现场作业单位详细信息查询数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取现场作业单位详细信息查询数据失败!");
        }
//        }
    }


    //企业违章详细信息查询
    @ApiOperation("企业违章详细信息查询")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/getSrpWorkViolationfile"}
    )
    public WrappedResult getSrpWorkViolationfile(HttpServletRequest request,  @RequestBody String jsonStr) {

        try {
            return WrappedResult.successWrapedResult(this.enterprisesadmittanceService.getSrpWorkViolationfile(jsonStr ));
        } catch (Exception var4) {
            log.error("获取企业违章详细信息查询数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取企业违章详细信息查询数据失败!");
        }

    }


    @ApiOperation("企业信息列表查询下拉框选项")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getSrpRiskSysTab"}
    )
    public WrappedResult getSrpRiskSysTab(HttpServletRequest request) {

        try {
            return WrappedResult.successWrapedResult(this.enterprisesadmittanceService.getSrpRiskSysTab());
        } catch (Exception var4) {
            log.error("获取企业信息列表查询下拉框选项数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取企业信息列表查询下拉框选项数据失败!");
        }
    }


    //企业违章详细信息查询
    @ApiOperation("企业信息列表查询省份级联单位下拉框选项")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getDatafillOrg"}
    )
    public WrappedResult getDatafillOrg(HttpServletRequest request, @RequestParam String datafillOrgId) {
//        String userId = ParamsUtil.getUserId(request);
//        if (StringUtils.isEmpty(userId)) {
//            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
//        } else {
        try {
            return WrappedResult.successWrapedResult(this.enterprisesadmittanceService.getDatafillOrg(datafillOrgId));
        } catch (Exception var4) {
            log.error("获取企业信息列表查询省份级联单位下拉框选项数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取企业信息列表查询省份级联单位下拉框选项数据失败!");
        }
//        }
    }


    @ApiOperation("企业违章详情记分规则")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getSrpworkViolationrules"}
    )
    public WrappedResult getSrpworkViolationrules(HttpServletRequest request, @RequestParam String violationfileId) {
//        String userId = ParamsUtil.getUserId(request);
//        if (StringUtils.isEmpty(userId)) {
//            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
//        } else {
        try {
            return WrappedResult.successWrapedResult(this.enterprisesadmittanceService.getSrpworkViolationrules(violationfileId));
        } catch (Exception var4) {
            log.error("获取企业违章详情记分规则数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取企业违章详情记分规则数据失败!");
        }
//        }
    }


    @ApiOperation("作业单位准入变更信息")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getSrpWorkSiteinfochange"}
    )
    public WrappedResult getSrpWorkSiteinfochange(HttpServletRequest request, @RequestParam String provinceCode,@RequestParam String basicinfoContractor) {
//        String userId = ParamsUtil.getUserId(request);
//        if (StringUtils.isEmpty(userId)) {
//            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
//        } else {
        try {
            return WrappedResult.successWrapedResult(this.enterprisesadmittanceService.getSrpWorkSiteinfochange(provinceCode ,basicinfoContractor));
        } catch (Exception var4) {
            log.error("作业单位准入变更信息数据失败 ", var4);
            return WrappedResult.failedWrappedResult("作业单位准入变更信息数据失败!");
        }
//        }
    }


    @ApiOperation("准入情况")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getSrpWorkSiteinfoAll"}
    )
    public WrappedResult getSrpWorkSiteinfoAll(HttpServletRequest request, @RequestParam String basicinfoContractor ,@RequestParam String siteinfoId) {
//        String userId = ParamsUtil.getUserId(request);
//        if (StringUtils.isEmpty(userId)) {
//            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
//        } else {
        try {
            return WrappedResult.successWrapedResult(this.enterprisesadmittanceService.getSrpWorkSiteinfoAll(basicinfoContractor ,siteinfoId));
        } catch (Exception var4) {
            log.error("获取企业准入情况数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取企业准入情况数据失败!");
        }
//        }
    }


    @ApiOperation("工作负责人,外包人员")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/getSrpWorksiteworker"}
    )
    public WrappedResult getSrpWorksiteworker(HttpServletRequest request, @RequestBody String jsonStr) {
//        String userId = ParamsUtil.getUserId(request);
//        if (StringUtils.isEmpty(userId)) {
//            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
//        } else {
        try {
            return WrappedResult.successWrapedResult(this.enterprisesadmittanceService.getSrpWorksiteworker(jsonStr));
        } catch (Exception var4) {
            log.error("获取工作负责人,外包人员数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取工作负责人,外包人员数据失败!");
        }
//        }
    }

    @ApiOperation("作业计划")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/getSrpWorkePlanDay"}
    )
    public WrappedResult getSrpWorkePlanDay(HttpServletRequest request, @RequestBody String jsonStr) {

        try {
            return WrappedResult.successWrapedResult(this.enterprisesadmittanceService.getSrpWorkePlanDay(jsonStr));
        } catch (Exception var4) {
            log.error("获取工作负责人,外包人员数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取工作负责人,外包人员数据失败!");
        }
    }


}
