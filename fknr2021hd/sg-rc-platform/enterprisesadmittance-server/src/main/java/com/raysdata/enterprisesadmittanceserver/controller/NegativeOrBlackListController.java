package com.raysdata.enterprisesadmittanceserver.controller;

import com.nariit.rmcp.common.vo.WrappedResult;
import com.raysdata.enterprisesadmittanceserver.servrce.NegativeOrBlackListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping({"/negativeOrBlackList"})
@Api("黑名单和负面清单接口api")
public class NegativeOrBlackListController {

    private static final String SESSION_TIMEOUT_TIP = "系统登录超时，请重新登录！";
    private final Logger log = LoggerFactory.getLogger(NegativeOrBlackListController.class);

    @Autowired
    NegativeOrBlackListService negativeOrBlackListService;


    //黑名单情况
    @ApiOperation("黑名单情况")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getBlackCntInfos"}
    )
    public WrappedResult getBlackCntInfos(HttpServletRequest request) {
//        String userId = ParamsUtil.getUserId(request);
//        if (StringUtils.isEmpty(userId)) {
//            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
//        } else {
        try {
            return WrappedResult.successWrapedResult(this.negativeOrBlackListService.getBlackCntInfos());
        } catch (Exception var4) {
            log.error("获取黑名单汇总数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取黑名单汇总数据失败!");
        }
//        }
    }


    //各单位本月纳入黑名单数及本月解除黑名单数情况
    @ApiOperation("各单位纳入黑名单数及解除黑名单数情况")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getBlackCntInfosByArea/{inteType}"}
    )
    public WrappedResult getBlackCntInfosByArea(HttpServletRequest request, @PathVariable Integer inteType) {
//        String userId = ParamsUtil.getUserId(request);
//        if (StringUtils.isEmpty(userId)) {
//            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
//        } else {
        try {
            return WrappedResult.successWrapedResult(this.negativeOrBlackListService.getBlackCntInfosByArea(inteType));
        } catch (Exception var4) {
            log.error("获取各单位本月纳入黑名单数及本月解除黑名单数情况数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取各单位本月纳入黑名单数及本月解除黑名单数情况数据失败!");
        }
//        }
    }


    //负面清单情况
    @ApiOperation("负面清单情况")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getNegativeCntInfos"}
    )
    public WrappedResult getNegativeCntInfos(HttpServletRequest request) {
//        String userId = ParamsUtil.getUserId(request);
//        if (StringUtils.isEmpty(userId)) {
//            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
//        } else {
        try {
            return WrappedResult.successWrapedResult(this.negativeOrBlackListService.getNegativeCntInfos());
        } catch (Exception var4) {
            log.error("获取负面清单汇总数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取负面清单汇总数据失败!");
        }
//        }
    }


    //各单位本月纳入负面清单数及本月解除负面清单数情况
    @ApiOperation("各单位本月纳入负面清单数及本月解除负面清单数情况")
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/getNegativeCntInfosByArea/{inteType}"}
    )
    public WrappedResult getNegativeCntInfosByArea(HttpServletRequest request,@PathVariable Integer inteType) {
//        String userId = ParamsUtil.getUserId(request);
//        if (StringUtils.isEmpty(userId)) {
//            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
//        } else {
        try {
            return WrappedResult.successWrapedResult(this.negativeOrBlackListService.getNegativeCntInfosByArea(inteType));
        } catch (Exception var4) {
            log.error("获取各单位本月纳入负面清单数及本月解除负面清单数情况数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取各单位本月纳入负面清单数及本月解除负面清单数情况数据失败!");
        }
//        }
    }


    //黑名单及负面清单列表
    @ApiOperation("黑名单及负面清单列表")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/getBlackInfosListOrNegativeInfosList"}
    )
    public WrappedResult getBlackInfosListOrNegativeInfosList(HttpServletRequest request, @RequestBody String jsonStr) {
//        String userId = ParamsUtil.getUserId(request);
//        if (StringUtils.isEmpty(userId)) {
//            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
//        } else {
        try {
            return WrappedResult.successWrapedResult(this.negativeOrBlackListService.getBlackInfosListOrNegativeInfosList(jsonStr));
        } catch (Exception var4) {
            log.error("获取黑名单及负面清单列表数据失败 ", var4);
            return WrappedResult.failedWrappedResult("获取黑名单及负面清单列表数据失败!");
        }
//        }
    }
    //删除企业列表数据
    @ApiOperation("删除黑名单及负面清单数据")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/deleteBlacklistInventory"}
    )
    public WrappedResult deleteInfo(HttpServletRequest request,@RequestBody String str) {

        try {
            return WrappedResult.successWrapedResult(this.negativeOrBlackListService.deleteBlacklistInventory(str));
        } catch (Exception var4) {
            log.error("删除列表数据失败 ", var4);
            return WrappedResult.failedWrappedResult("删除列表数据失败!");
        }
    }


}
