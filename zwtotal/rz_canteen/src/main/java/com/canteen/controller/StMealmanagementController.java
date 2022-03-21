package com.canteen.controller;

import com.canteen.entity.StMealmanagement;
import com.canteen.service.StMealmanagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/StMealmanagement.do")
@Slf4j
@CrossOrigin
public class StMealmanagementController {

    @Autowired
    private StMealmanagementService stMealmanagementService;

    //直接取餐列表
    @RequestMapping(value = "/directList",method = RequestMethod.POST)
    public Object directList(@RequestBody String param){
        try {
            return stMealmanagementService.directList(param);
        }catch (Exception e){
            e.printStackTrace();
            return "获取取餐列表失败";
        }
    }

    //餐柜取餐列表
    @RequestMapping(value = "/plateList",method = RequestMethod.POST)
    public Object plateList(@RequestBody String param){
        try {
            return stMealmanagementService.plateList(param);
        }catch (Exception e){
            e.printStackTrace();
            return "获取取餐列表失败";
        }
    }



    //更改取餐方式
    @RequestMapping(value = "/updateMode",method = RequestMethod.POST)
    public Object updateMode(@RequestBody StMealmanagement stMealmanagement){
        try {
            return stMealmanagementService.updateMode(stMealmanagement);
        }catch (Exception e){
            e.printStackTrace();
            return "更改取餐方式失败";
        }
    }

    //下拉框数据
    @RequestMapping(value = "/dropDownBox",method = RequestMethod.GET)
    public Object dropDownBox(){
        try {
            return stMealmanagementService.dropDownBox();
        }catch (Exception e){
            e.printStackTrace();
            return "获取下拉框数据失败";
        }
    }
}
