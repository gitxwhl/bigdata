package com.officeServices.controller;

import com.officeServices.entity.TdeptBudget;
import com.officeServices.entity.Tevaluate;
import com.officeServices.service.TdeptBudgetService;
import com.officeServices.utils.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/TdeptBudget.do")
@Slf4j
@CrossOrigin
public class TdeptBudgetController {

    @Autowired
    private TdeptBudgetService tdeptBudgetService;

    /**
     * 查询预算列表
     */
    @RequestMapping(value = "/getBudget", method = RequestMethod.POST)
    public Object getBudget(@RequestBody Map<String,Object> map){
        try {
            return tdeptBudgetService.getBudget(map);
        }catch (Exception e){
            e.printStackTrace();
            return "查询预算列表异常";
        }
    }

    /**
     * 预算设置
     */
    @RequestMapping(value = "/insertBudget", method = RequestMethod.POST)
    public Object insertBudget(@RequestBody TdeptBudget tdeptBudget){
        try {
            return tdeptBudgetService.insertBudget(tdeptBudget);
        }catch (Exception e){
            e.printStackTrace();
            return "预算设置异常";
        }
    }

    /**
     * 导入年度预算
     */
    @RequestMapping(value = "/importBudget", method = RequestMethod.POST)
    public Object importBudget(HttpServletRequest request){
        ResultMessage resultMessage = new ResultMessage();
        try {
            Object o = tdeptBudgetService.importBudget(request);
            resultMessage.setMessage(o.toString());
        }catch (Exception e){
            e.printStackTrace();
            resultMessage.setMessage("导入年度预算失败");
        }
        return resultMessage;
    }

    /**
     * 下拉框
     */
    @RequestMapping(value = "/dropDownBox", method = RequestMethod.GET)
    public Object dropDownBox(){
        try {
            return tdeptBudgetService.dropDownBox();
        }catch (Exception e){
            e.printStackTrace();
            return "获取下拉框异常";
        }
    }

    /**
     * 查询申请信息
     */
    @RequestMapping(value = "/getBudgetInfo", method = RequestMethod.POST)
    public Object getBudgetInfo(@RequestBody Map<String,Object> map){
        try {
            return tdeptBudgetService.getBudgetInfo(map);
        }catch (Exception e){
            e.printStackTrace();
            return "查询申请信息异常";
        }
    }

    /**
     * 用户查询申请情况
     */
    @RequestMapping(value = "/getSituation", method = RequestMethod.POST)
    public Object getSituation(@RequestBody Map<String,Object> map){
        try {
            return tdeptBudgetService.getSituation(map);
        }catch (Exception e){
            e.printStackTrace();
            return "用户查询申请情况异常";
        }
    }

    /**
     * 用户查询评价列表
     */
    @RequestMapping(value = "/getPjList", method = RequestMethod.POST)
    public Object getPjList(@RequestBody Map<String,Object> map){
        try {
            return tdeptBudgetService.getPjList(map);
        }catch (Exception e){
            e.printStackTrace();
            return "用户查询评价列表异常";
        }
    }

    /**
     * 用户评价
     */
    @RequestMapping(value = "/evaluate", method = RequestMethod.POST)
    public Object evaluate(@RequestBody List<Tevaluate> list){
        try {
            return tdeptBudgetService.evaluate(list);
        }catch (Exception e){
            e.printStackTrace();
            return "评价失败";
        }
    }
}
