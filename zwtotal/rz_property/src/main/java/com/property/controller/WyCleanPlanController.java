package com.property.controller;

import com.github.pagehelper.Page;
import com.property.service.WyCleanPlanService;
import com.property.utils.PageBean;
import com.property.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/WyCleanPlan.do")
@Slf4j
@CrossOrigin
public class WyCleanPlanController {
    @Autowired
    WyCleanPlanService wyCleanPlanService;

    /**
     * 保洁计划查询
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/selectCleanPlan", method = RequestMethod.POST)
    private PageBean selectCleanPlan(HttpServletRequest request, @RequestBody String param){
        return wyCleanPlanService.selectCleanPlan(param);
    }

    /**
     * 删除保洁计划
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/deleteCleanPlan",method = RequestMethod.POST)
    private Result deleteCleanPlan(HttpServletRequest request,@RequestBody String param){
         Result result = new Result();
         try {
             wyCleanPlanService.deleteCleanPlan(param);
             result.setMessage("删除成功");
         }catch (Exception e){
             e.printStackTrace();
             result.setMessage("删除失败");
         }
         return result;
    }

    /**
     * 修改-根据id返回相关信息
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateOfContent",method = RequestMethod.POST)
    private Map updateOfContent(HttpServletRequest request,@RequestBody String param){
        return wyCleanPlanService.updateOfContent(param);
    }

    /**
     * 修改保洁计划
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateCleanPlan",method = RequestMethod.POST)
    private Result updateCleanPlan(HttpServletRequest request,@RequestBody String param){
        Result result = new Result();
        try {
            wyCleanPlanService.updateCleanPlan(param);
            result.setMessage("修改成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("修改失败");
        }
        return result;
    }

    /**
     * 新建保洁计划
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/insertCleanPlan",method = RequestMethod.POST)
    private Result insertCleanPlan(HttpServletRequest request,@RequestBody String param){
        Result result = new Result();
        try {
            wyCleanPlanService.insertCleanPlan(param);
            result.setMessage("新建成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("新建失败");
        }
        return result;
    }

    /**
     * 检查计划查询
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/selectInspectionPlan",method = RequestMethod.POST)
    private PageBean selectInspectionPlan(HttpServletRequest request, @RequestBody String param){
        return wyCleanPlanService.selectInspectionPlan(param);
    }


    /**
     * 删除检查计划
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/deleteInspectionPlan",method = RequestMethod.POST)
    private Result deleteInspectionPlan(HttpServletRequest request,@RequestBody String param){
        Result result = new Result();
        try {
            wyCleanPlanService.deleteInspectionPlan(param);
            result.setMessage("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 修改-根据id查询信息
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/selectInspectionPlanById",method = RequestMethod.POST)
    private Map selectInspectionPlanById(HttpServletRequest request,@RequestBody String param){
        return wyCleanPlanService.selectInspectionPlanById(param);
    }

    /**
     * 修改检查计划
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateInspectionPlan",method = RequestMethod.POST)
    private Result updateInspectionPlan(HttpServletRequest request,@RequestBody String param){
        Result result = new Result();
        try {
            wyCleanPlanService.updateInspectionPlan(param);
            result.setMessage("修改成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("修改失败");
        }
        return result;
    }

    /**
     * 新建检查计划
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/insertInspectionPlan",method = RequestMethod.POST)
    private Result insertInspectionPlan(HttpServletRequest request,@RequestBody String param){
        Result result = new Result();
        try {
            wyCleanPlanService.insertInspectionPlan(param);
            result.setMessage("新建成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("新建失败");
        }
        return result;
    }
}
