package com.property.controller;

import com.property.service.WyInspectionAndAnalysisService;
import com.property.utils.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/WyInspectionAndAnalysis.do")
@Slf4j
@CrossOrigin
public class WyInspectionAndAnalysisController {
    @Autowired
    WyInspectionAndAnalysisService wyInspectionAndAnalysisService;

    /**
     * 保洁计划检查查询
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/selectInspectionAndAnalysis",method = RequestMethod.POST)
    private PageBean selectInspectionAndAnalysis(HttpServletRequest request,
                                                 @RequestBody String param){
     return wyInspectionAndAnalysisService.selectInspectionAndAnalysis(param);
    }

    /**
     * 保洁情况统计
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/CleaningStatistics",method = RequestMethod.POST)
    private Map CleaningStatistics(HttpServletRequest request,@RequestBody String param){
        return wyInspectionAndAnalysisService.CleaningStatistics(param);
    }

    /**
     * 计划完成情况统计
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/PlanCompletionStatistics",method = RequestMethod.POST)
    private Map PlanCompletionStatistics(HttpServletRequest request,@RequestBody String param){
        return wyInspectionAndAnalysisService.PlanCompletionStatistics(param);
    }


}
