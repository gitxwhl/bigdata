package com.raysdata.enterprisesadmittanceserver.controller;


import com.nariit.rmcp.common.vo.WrappedResult;
import com.raysdata.enterprisesadmittanceserver.servrce.personnelInformationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping({"/personnelInformation"})
@Api("企业准入人员接口api")
public class personnelInformationController {
    private static final String SESSION_TIMEOUT_TIP = "系统登录超时，请重新登录！";
    private final Logger log = LoggerFactory.getLogger(personnelInformationController.class);

    @Autowired
    private personnelInformationService personInService;


    //查询：登录认证token
    @ApiOperation("获取登录认证token")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/loginToken/{token}"}
    )
    public WrappedResult loginToken(HttpServletRequest request, @PathVariable String token) {
        try {
            return WrappedResult.successWrapedResult(this.personInService.loginToken(token));
        } catch (Exception var4) {
            log.error("获取登录认证token数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取登录认证token数据失败!");
        }
    }


    @ApiOperation("人员模板导出")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/downloadPerExcel"}
    )
    public WrappedResult downloadPerExcel(HttpServletRequest request, HttpServletResponse response) {
        try {
            return WrappedResult.successWrapedResult(this.personInService.downloadPerExcel(request,response));
        } catch (Exception var4) {
            this.log.error("模板文件导出失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("模板文件导出失败!");
        }
    }


    //查询：作业人员总数，业主人数，集体人数，外包人数，  当前有效准入人数，本月新增准入人数，本月取消资格人数。
    @ApiOperation("全网准入人员总数")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/totalStaff"}
    )
    public WrappedResult totalStaff(HttpServletRequest request, @RequestParam String Month) {
        try {
            return WrappedResult.successWrapedResult(this.personInService.getTotalStaff(Month));
        } catch (Exception var4) {
            log.error("获取全网准入单位情况汇总数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取全网准入单位情况汇总数据失败!");
        }
    }


    @ApiOperation("地区，人员性质分组查询人数")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getRegionalMonth"}
    )
    public WrappedResult getRegionalMonth(HttpServletRequest request, @RequestParam String Month) {

        try {
            return WrappedResult.successWrapedResult(this.personInService.getRegionalMonth(Month));
        } catch (Exception var4) {
            log.error("获取工作负责人,外包人员数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取工作负责人,外包人员数据失败!");
        }
    }

    //页码 数量 姓名 单位 省份 专业 企业性质 三种人标识 开始时间 结束时间  是否有效
    @ApiOperation("地区，人员性质分组查询人数")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/getPersonnelIn"}
    )
    public WrappedResult getPersonnelIn(HttpServletRequest request,  @RequestBody String jsonStr) {
        try {
            return WrappedResult.successWrapedResult(this.personInService.getPersonnelIn(jsonStr));
        } catch (Exception var4) {
            log.error("获取工作负责人,外包人员数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取工作负责人,外包人员数据失败!");
        }
    }

    //删除企业列表数据
    @ApiOperation("删除人员列表数据")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/deletePersonnel"}
    )
    public WrappedResult deleteInfo(HttpServletRequest request,@RequestBody String str) {

        try {
            return WrappedResult.successWrapedResult(this.personInService.deletePersonnel(str));
        } catch (Exception var4) {
            log.error("删除列表数据失败 ", var4);
            return WrappedResult.failedWrappedResult("删除列表数据失败!");
        }
    }

    @ApiOperation("省份小代码")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getDatareportOrg"}
    )
    public WrappedResult getDatareportOrg(HttpServletRequest request) {
        try {
            return WrappedResult.successWrapedResult(this.personInService.getDatareportOrg());
        } catch (Exception var4) {
            log.error("获取工作负责人,外包人员数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取工作负责人,外包人员数据失败!");
        }
    }

    //根据省份查询单位
    @ApiOperation("单位小代码")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getOrgId"}
    )
    public WrappedResult getOrgId(HttpServletRequest request, @RequestParam String OrgId) {
        try {
            return WrappedResult.successWrapedResult(this.personInService.getOrgId(OrgId));
        } catch (Exception var4) {
            log.error("获取工作负责人,外包人员数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取工作负责人,外包人员数据失败!");
        }
    }

    @ApiOperation("专业小代码")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getProfession"}
    )
    public WrappedResult getProfession(HttpServletRequest request) {
        try {
            return WrappedResult.successWrapedResult(this.personInService.getProfession());
        } catch (Exception var4) {
            log.error("获取工作负责人,外包人员数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取工作负责人,外包人员数据失败!");
        }
    }

    @ApiOperation("人员性质")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getOrgNature"}
    )
    public WrappedResult getOrgNature(HttpServletRequest request) {
        try {
            return WrappedResult.successWrapedResult(this.personInService.getOrgNature());
        } catch (Exception var4) {
            log.error("人员性质失败 ", var4);
            return WrappedResult.failedWrappedResult("人员性质失败!");
        }
    }

    @ApiOperation("三种人标识小代码")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getThreekindsIdentification"}
    )
    public WrappedResult getThreekindsIdentification(HttpServletRequest request) {
        try {
            return WrappedResult.successWrapedResult(this.personInService.getThreekindsIdentification());
        } catch (Exception var4) {
            log.error("获取工作负责人,外包人员数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取工作负责人,外包人员数据失败!");
        }
    }

    @ApiOperation("是否有效小代码")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getAccessState"}
    )
    public WrappedResult getAccessState(HttpServletRequest request) {
        try {
            return WrappedResult.successWrapedResult(this.personInService.getAccessState());
        } catch (Exception var4) {
            log.error("获取工作负责人,外包人员数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取工作负责人,外包人员数据失败!");
        }
    }

    @ApiOperation("性别")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getSex"}
    )
    public WrappedResult getSex(HttpServletRequest request) {
        try {
            return WrappedResult.successWrapedResult(this.personInService.getSex());
        } catch (Exception var4) {
            log.error("获取工作负责人,外包人员数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取工作负责人,外包人员数据失败!");
        }
    }

    @ApiOperation("根据人员id查询人员")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getOrgids"}
    )
    public WrappedResult getOrgids(HttpServletRequest request, @RequestParam String siteworkerinfoId) {
        try {
            return WrappedResult.successWrapedResult(this.personInService.getOrgids(siteworkerinfoId));
        } catch (Exception var4) {
            log.error("获取工作负责人,外包人员数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取工作负责人,外包人员数据失败!");
        }
    }



    @ApiOperation("根据人员id查询考试成绩")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getExamination"}
    )
    public WrappedResult getExamination(HttpServletRequest request, @RequestParam String siteworkerinfoId) {
        try {
            return WrappedResult.successWrapedResult(this.personInService.getExamination(siteworkerinfoId));
        } catch (Exception var4) {
            log.error("获取工作负责人,外包人员数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取工作负责人,外包人员数据失败!");
        }
    }

    @ApiOperation("根据人员id查询违章记录")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getViolationfile"}
    )
    public WrappedResult getViolationfile(HttpServletRequest request, @RequestParam String siteworkerinfoId) {
        try {
            return WrappedResult.successWrapedResult(this.personInService.getViolationfile(siteworkerinfoId));
        } catch (Exception var4) {
            log.error("获取工作负责人,外包人员数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取工作负责人,外包人员数据失败!");
        }
    }

    @ApiOperation("人员变更记录")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getDatareport"}
    )
    public WrappedResult getDatareport(HttpServletRequest request, @RequestParam String siteworkerinfoId) {
        try {
            return WrappedResult.successWrapedResult(this.personInService.getDatareport(siteworkerinfoId));
        } catch (Exception var4) {
            log.error("人员变更记录失败 ", var4);
            return WrappedResult.failedWrappedResult("人员变更记录失败!");
        }
    }

    @ApiOperation("根据人员id查询作业计划")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/getSrpWorkePlanDay"}
    )
    public WrappedResult getSrpWorkePlanDay(HttpServletRequest request, @RequestBody String jsonStr) {
        try {
            return WrappedResult.successWrapedResult(this.personInService.getSrpWorkePlanDay(jsonStr));
        } catch (Exception var4) {
            log.error("获取工作负责人,外包人员数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取工作负责人,外包人员数据失败!");
        }
    }

    @ApiOperation("人员头像展示")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getTmxAttachment"}
    )
    public WrappedResult getTmxAttachment(HttpServletRequest request, @RequestParam String siteworkerinfoId) {
        try {
            return WrappedResult.successWrapedResult(this.personInService.getTmxAttachment(siteworkerinfoId));
        } catch (Exception var4) {
            log.error("获取工作负责人,外包人员数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取工作负责人,外包人员数据失败!");
        }
    }


}
