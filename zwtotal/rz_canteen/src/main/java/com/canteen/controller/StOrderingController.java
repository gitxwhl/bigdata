package com.canteen.controller;

import com.canteen.entity.StCabinet;
import com.canteen.service.StOrderingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/StOrdering.do")
@Slf4j
@CrossOrigin
public class StOrderingController {

    @Autowired
    private StOrderingService stOrderingService;

    //线上订餐列表查询
    @RequestMapping(value = "/getOrderList", method = RequestMethod.POST)
    private Object getOrderList(HttpServletRequest request, @RequestBody String param){
        try {
            return stOrderingService.getOrderList(param);
        }catch (Exception e){
            e.printStackTrace();
            return "线上订餐列表查询失败";
        }
    }

    //线上预订
    @RequestMapping(value = "/reservation", method = RequestMethod.POST)
    private Object reservation(HttpServletRequest request, HttpServletResponse response, @RequestBody String param){
        try {
            return stOrderingService.reservation(param);
        }catch (Exception e){
            e.printStackTrace();
            return "线上预订失败";
        }
    }

    //预订菜品详情
    @RequestMapping(value = "/getOrderInfo", method = RequestMethod.POST)
    private Object getOrderInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody String param){
        try {
            return stOrderingService.getOrderInfo(param);
        }catch (Exception e){
            e.printStackTrace();
            return "获取预订菜品详情失败";
        }
    }

    //取消预订
    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    private Object cancelOrder(HttpServletRequest request, HttpServletResponse response, @RequestBody String param){
        try {
            return stOrderingService.cancelOrder(param);
        }catch (Exception e){
            e.printStackTrace();
            return "取消失败";
        }
    }

    //查询菜品信息
    @RequestMapping(value = "/getDishes", method = RequestMethod.POST)
    private Object getDishes(HttpServletRequest request, @RequestBody String param){
        try {
            return stOrderingService.getDishes(param);
        }catch (Exception e){
            e.printStackTrace();
            return "查询菜品信息失败";
        }
    }

    //下拉框数据
    @RequestMapping(value = "/dropDownBox", method = RequestMethod.GET)
    private Object dropDownBox(HttpServletRequest request){
        try {
            return stOrderingService.dropDownBox();
        }catch (Exception e){
            e.printStackTrace();
            return "获取下拉框数据失败";
        }
    }

    //--------------------------------------------打包服务-------------------------------------------------------
    //打包服务列表查询
    @RequestMapping(value = "/getPackageService", method = RequestMethod.POST)
    private Object getPackageService(HttpServletRequest request, @RequestBody String param){
        try {
            return stOrderingService.getPackageService(param);
        }catch (Exception e){
            e.printStackTrace();
            return "打包服务列表查询失败";
        }
    }

    //打包上柜
    @RequestMapping(value = "/packaging", method = RequestMethod.POST)
    private Object packaging(HttpServletRequest request, @RequestBody StCabinet stCabinet){
        try {
            return stOrderingService.packaging(stCabinet);
        }catch (Exception e){
            e.printStackTrace();
            return "打包上柜失败";
        }
    }

    //下拉框数据
    @RequestMapping(value = "/getDropDownBox", method = RequestMethod.GET)
    private Object getDropDownBox(HttpServletRequest request){
        try {
            return stOrderingService.getDropDownBox();
        }catch (Exception e){
            e.printStackTrace();
            return "获取下拉框数据失败";
        }
    }

    //--------------------------------------------异地就餐申请-------------------------------------------------------
    //异地配送列表查询
    @RequestMapping(value = "/distributionList", method = RequestMethod.POST)
    private Object distributionList(HttpServletRequest request, @RequestBody String param){
        try {
            return stOrderingService.distributionList(param);
        }catch (Exception e){
            e.printStackTrace();
            return "异地配送列表查询失败";
        }
    }
}
