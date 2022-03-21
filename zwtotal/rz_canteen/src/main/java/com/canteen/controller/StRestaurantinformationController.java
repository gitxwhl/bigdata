package com.canteen.controller;

import com.canteen.service.StRestaurantinformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/stRestaurantinformation.do")
@Slf4j
@CrossOrigin
public class StRestaurantinformationController {

    @Autowired
    private StRestaurantinformationService stRestaurantinformationService;

    //餐厅信息
    @RequestMapping(value = "/insertStRestaurantinformation", method = RequestMethod.POST)
    private Object insertStRestaurantinformation(HttpServletRequest request, @RequestBody String param){
        try {
            return stRestaurantinformationService.insertStRestaurantinformation(param);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "查询餐厅信息失败";
    }
    //单击修改餐厅信息及餐厅人数失败
    @RequestMapping(value = "/updateMealNumber", method = RequestMethod.POST)
    private Object updateMealNumber(HttpServletRequest request, @RequestBody String param){
        try {
            return stRestaurantinformationService.updateMealNumber(param);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "单击修改餐厅信息及餐厅人数失败";
    }


    //保存
    @RequestMapping(value = "/updateMeal", method = RequestMethod.POST)
    private Object updateMeal(HttpServletRequest request, @RequestBody String param){
        try {
            return stRestaurantinformationService.updateMeal(param);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "修改失败";
    }

}
