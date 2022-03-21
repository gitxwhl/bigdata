package com.canteen.controller;


import com.canteen.entity.StOnlinemanagement;
import com.canteen.service.StOnlinemanagementService;
import com.canteen.service.StfoodManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/StOnlinemanagement.do")
@Slf4j
@CrossOrigin
public class StOnlinemanagementController {

    @Autowired
    private StOnlinemanagementService stOnlinemanagementService;

    //线上菜品管理列表
    @RequestMapping(value = "/getOnlineList", method = RequestMethod.POST)
    private Object getOnlineList(HttpServletRequest request, @RequestBody String param){
        try {
            return stOnlinemanagementService.getOnlineList(param);
        }catch (Exception e){
            e.printStackTrace();
            return "获取线上菜品管理列表失败";
        }
    }

    //是否支持线上预订
    @RequestMapping(value = "/updateReserve", method = RequestMethod.POST)
    private Object updateReserve(HttpServletRequest request, @RequestBody StOnlinemanagement stOnlinemanagement){
        try {
            return stOnlinemanagementService.updateReserve(stOnlinemanagement);
        }catch (Exception e){
            e.printStackTrace();
            return "修改失败";
        }
    }

    //是否支持配送
    @RequestMapping(value = "/updateDistribution", method = RequestMethod.POST)
    private Object updateDistribution(HttpServletRequest request, @RequestBody StOnlinemanagement stOnlinemanagement){
        try {
            return stOnlinemanagementService.updateDistribution(stOnlinemanagement);
        }catch (Exception e){
            e.printStackTrace();
            return "修改失败";
        }
    }

    //获取下拉框数据
    @RequestMapping(value = "/dropDownBox", method = RequestMethod.GET)
    private Object dropDownBox(HttpServletRequest request){
        try {
            return stOnlinemanagementService.dropDownBox();
        }catch (Exception e){
            e.printStackTrace();
            return "获取下拉框数据失败";
        }
    }

    //营养成分分析
    @RequestMapping(value = "/getNutritional", method = RequestMethod.POST)
    private Object getNutritional(HttpServletRequest request, @RequestBody String param){
        try {
            return stOnlinemanagementService.getNutritional(param);
        }catch (Exception e){
            e.printStackTrace();
            return "营养成分分析失败";
        }
    }
}
