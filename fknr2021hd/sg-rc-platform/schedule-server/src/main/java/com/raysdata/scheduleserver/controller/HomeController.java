package com.raysdata.scheduleserver.controller;

import com.nariit.rmcp.common.util.ParamsUtil;
import com.nariit.rmcp.common.vo.WrappedResult;

import javax.servlet.http.HttpServletRequest;

import com.raysdata.scheduleserver.service.HomeService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/home"})
public class HomeController {

    private static final String SESSION_TIMEOUT_TIP = "系统登录超时，请重新登录！";
    private final Logger log = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private HomeService homeService;

    public HomeController() {
    }

    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/workplan/list"}
    )
    public WrappedResult getWorkPlanList(HttpServletRequest request) {
        String userId = ParamsUtil.getUserId(request);
        if (StringUtils.isEmpty(userId)) {
            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
        } else {
            try {
                return WrappedResult.successWrapedResult(this.homeService.getWorkPlanList(userId));
            } catch (Exception var4) {
                log.error("获取作业计划一览数据失败 ", var4);
                return WrappedResult.failedWrappedResult("获取作业计划一览数据失败!");
            }
        }
    }

    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/zyaq/risk"}
    )
    public WrappedResult getZyaqRisk(HttpServletRequest request) {
        String userId = ParamsUtil.getUserId(request);
        if (StringUtils.isEmpty(userId)) {
            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
        } else {
            try {
                return WrappedResult.successWrapedResult(this.homeService.getZyaqRisk(userId));
            } catch (Exception var4) {
                log.error("获取作业风险等级统计数据失败 ", var4);
                return WrappedResult.failedWrappedResult("获取作业风险等级统计数据失败!");
            }
        }
    }

    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/exe/state"}
    )
    public WrappedResult getExeState(HttpServletRequest request) {
        String userId = ParamsUtil.getUserId(request);
        if (StringUtils.isEmpty(userId)) {
            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
        } else {
            try {
                return WrappedResult.successWrapedResult(this.homeService.getExeState(userId));
            } catch (Exception var4) {
                log.error("获取今日施工现场统计数据失败 ", var4);
                return WrappedResult.failedWrappedResult("获取今日施工现场统计数据失败!");
            }
        }
    }

    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/dwyx/risk"}
    )
    public WrappedResult getDwfxRisk(HttpServletRequest request) {
        String userId = ParamsUtil.getUserId(request);
        if (StringUtils.isEmpty(userId)) {
            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
        } else {
            try {
                return WrappedResult.successWrapedResult(this.homeService.getDwfxRisk(userId));
            } catch (Exception var4) {
                log.error("获取电网风险等级统计数据失败 ", var4);
                return WrappedResult.failedWrappedResult("获取电网风险等级统计数据失败!");
            }
        }
    }

    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/sg/risk"}
    )
    public WrappedResult getSgRisk(HttpServletRequest request) {
        String userId = ParamsUtil.getUserId(request);
        if (StringUtils.isEmpty(userId)) {
            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
        } else {
            try {
                return WrappedResult.successWrapedResult(this.homeService.getSgRisk(userId));
            } catch (Exception var4) {
                log.error("获取施工风险等级统计数据失败 ", var4);
                return WrappedResult.failedWrappedResult("获取施工风险等级统计数据失败!");
            }
        }
    }

    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/zyglbm/type"}
    )
    public WrappedResult getZyglbmType(HttpServletRequest request) {
        String userId = ParamsUtil.getUserId(request);
        if (StringUtils.isEmpty(userId)) {
            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
        } else {
            try {
                return WrappedResult.successWrapedResult(this.homeService.getZyglbmType(userId));
            } catch (Exception var4) {
                log.error("获取管理专业统计数据失败 ", var4);
                return WrappedResult.failedWrappedResult("获取管理专业统计数据失败!");
            }
        }
    }

    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/wp/map"}
    )
    public WrappedResult getWorkplanMap(HttpServletRequest request) {
        String userId = ParamsUtil.getUserId(request);
        if (StringUtils.isEmpty(userId)) {
            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
        } else {
            try {
                String riskLevel = request.getParameter("riskLevel");
                return WrappedResult.successWrapedResult(this.homeService.getWorkplanMap(userId, riskLevel));
            } catch (Exception var4) {
                log.error("获取作业计划地图分布数据失败 ", var4);
                return WrappedResult.failedWrappedResult("获取作业计划地图分布数据失败!");
            }
        }
    }

    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/wp/map/{workPlanId}"}
    )
    public WrappedResult getWorkplanMapById(HttpServletRequest request, @PathVariable String workPlanId) {
        String userId = ParamsUtil.getUserId(request);
        if (StringUtils.isEmpty(userId)) {
            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
        } else {
            try {
                return WrappedResult.successWrapedResult(this.homeService.getWorkplanMapById(userId, workPlanId));
            } catch (Exception var5) {
                log.error("获取地图上作业计划详情数据失败 ", var5);
                return WrappedResult.failedWrappedResult("获取地图上作业计划详情数据失败!");
            }
        }
    }

    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/peccancy/list"}
    )
    public WrappedResult getPeccancyList(HttpServletRequest request) {
        String userId = ParamsUtil.getUserId(request);
        if (StringUtils.isEmpty(userId)) {
            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
        } else {
            try {
                return WrappedResult.successWrapedResult(this.homeService.getPeccancyList(userId));
            } catch (Exception var4) {
                log.error("获取违章统计一览数据失败 ", var4);
                return WrappedResult.failedWrappedResult("获取违章统计一览数据失败!");
            }
        }
    }

    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/peccancy/{id}"}
    )
    public WrappedResult getPeccancyById(HttpServletRequest request, @PathVariable String id) {
        String userId = ParamsUtil.getUserId(request);
        if (StringUtils.isEmpty(userId)) {
            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
        } else {
            try {
                return WrappedResult.successWrapedResult(this.homeService.getPeccancyById(userId, id));
            } catch (Exception var5) {
                log.error("获取违章详情数据失败 ", var5);
                return WrappedResult.failedWrappedResult("获取违章详情数据失败!");
            }
        }
    }

    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/peccancy/amount"}
    )
    public WrappedResult getPeccancyAmount(HttpServletRequest request) {
        String userId = ParamsUtil.getUserId(request);
        if (StringUtils.isEmpty(userId)) {
            return WrappedResult.failedWrappedResult("系统登录超时，请重新登录！");
        } else {
            try {
                return WrappedResult.successWrapedResult(this.homeService.getPeccancyAmount(userId));
            } catch (Exception var4) {
                log.error("获取违章数量数据失败 ", var4);
                return WrappedResult.failedWrappedResult("获取违章数量数据失败!");
            }
        }
    }

}
