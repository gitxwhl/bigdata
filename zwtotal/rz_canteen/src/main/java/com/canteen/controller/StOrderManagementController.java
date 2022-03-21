package com.canteen.controller;

import com.canteen.service.StOrderManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/StOrderManagement.do")
@Slf4j
@CrossOrigin
public class StOrderManagementController {

    @Autowired
    private StOrderManagementService stOrderManagementService;

    //订单列表查询
    @RequestMapping(value = "/getOrder", method = RequestMethod.POST)
    private Object getOrder(HttpServletRequest request, @RequestBody String param){
        try {
            return stOrderManagementService.getOrder(param);
        }catch (Exception e){
            e.printStackTrace();
            return "获取订单列表失败";
        }
    }

    //通过订单号查询订单详情
    @RequestMapping(value = "/getOrderByNo", method = RequestMethod.POST)
    private Object getOrderByNo(HttpServletRequest request, @RequestBody String param){
        try {
            return stOrderManagementService.getOrderByNo(param);
        }catch (Exception e){
            e.printStackTrace();
            return "获取订单详情失败";
        }
    }

    //获取下拉框数
    @RequestMapping(value = "/dropDownBox", method = RequestMethod.GET)
    private Object dropDownBox(HttpServletRequest request){
        try {
            return stOrderManagementService.dropDownBox();
        }catch (Exception e){
            e.printStackTrace();
            return "获取下拉框数据失败";
        }
    }
}
