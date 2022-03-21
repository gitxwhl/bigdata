package com.canteen.controller;

import com.canteen.entity.StMenuPlanning;
import com.canteen.service.StMenuPlanningService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/StMenuPlanning.do")
@Slf4j
@CrossOrigin
public class StMenuPlanningController {

    @Autowired
    StMenuPlanningService stMenuPlanningService;

    //菜单计划查询
    @RequestMapping(value = "/getMenus",method = RequestMethod.POST)
    public Object getMenus(HttpServletRequest request, @RequestBody String param){
        try {
            return stMenuPlanningService.getMenus(param);
        }catch (Exception e){
            e.printStackTrace();
            return "获取列表失败";
        }
    }

    //菜单计划查询
    @RequestMapping(value = "/getRelevance",method = RequestMethod.GET)
    public Object getRelevance(HttpServletRequest request){
        try {
            return stMenuPlanningService.getRelevance();
        }catch (Exception e){
            e.printStackTrace();
            return "获取关联属性失败";
        }
    }

    //编辑订餐及退餐时间
    @RequestMapping(value = "/updateMenuPlan",method = RequestMethod.POST)
    public Object updateMenuPlan(HttpServletRequest request, @RequestBody StMenuPlanning stMenuPlanning){
        try {
            return stMenuPlanningService.updateMenuPlan(stMenuPlanning);
        }catch (Exception e){
            e.printStackTrace();
            return "编辑失败";
        }
    }


}
