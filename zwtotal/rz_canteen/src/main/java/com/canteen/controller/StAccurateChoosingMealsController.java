package com.canteen.controller;

import com.canteen.service.StAccurateChoosingMealsService;
import com.canteen.utils.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/stAccurateChoosingMeals.do")
@Slf4j
@CrossOrigin
public class StAccurateChoosingMealsController {

    @Autowired
    private StAccurateChoosingMealsService stAccurateChoosingMealsService;

    //查询餐盘种类
    @RequestMapping(value = "/getDiningRoom",method = RequestMethod.POST)
    private Object getDiningRoom(HttpServletRequest request){
        return stAccurateChoosingMealsService.getDiningRoom();
    }

    //获取营养列表
    @RequestMapping(value = "/getNutritionList", method = RequestMethod.POST)
    private PageBean getNutritionList(HttpServletRequest request, @RequestBody String param){
        return stAccurateChoosingMealsService.getNutritionList(param);
    }
    //添加营养
    @RequestMapping(value = "/addNutrition", method = RequestMethod.POST)
    private Object addNutrition(HttpServletRequest request, @RequestBody String paramJson){
        return stAccurateChoosingMealsService.addNutrition(paramJson);
    }

    //删除营养数据
    @RequestMapping(value = "/deleteNutrition", method = RequestMethod.POST)
    private String deleteNutrition(HttpServletRequest request, @RequestBody String param){
        return stAccurateChoosingMealsService.deleteNutrition(param);
    }
    //自动打菜 修改状态
    @RequestMapping(value = "/updateState", method = RequestMethod.POST)
    private String updateState(HttpServletRequest request, @RequestBody String param){
            return stAccurateChoosingMealsService.updateState(param);
    }


}
