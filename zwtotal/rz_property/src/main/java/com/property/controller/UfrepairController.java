package com.property.controller;


import com.property.entity.Trepaircheck;
import com.property.entity.Ufrepair;
import com.property.service.UfrepairService;
import com.property.utils.MySFTP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/Ufrepair.do")
@Slf4j
@CrossOrigin
public class UfrepairController {

    @Autowired
    private UfrepairService ufrepairService;

    //部门导航栏
    @RequestMapping(value = "/getWycorporateList",method = RequestMethod.GET)
    public Object getWycorporateList(HttpServletRequest request) {
        try {
            return ufrepairService.getWycorporateList();
        }catch (Exception e){
            e.printStackTrace();
            return "获取部门导航栏失败";
        }
    }

    //提交报修申请
    @RequestMapping(value = "/addUfrepair",method = RequestMethod.POST)
    public Object addUfrepair(HttpServletRequest request, @RequestBody Ufrepair ufrepair) {
        try {
            return ufrepairService.addUfrepair(ufrepair);
        }catch (Exception e){
            e.printStackTrace();
            return "申请失败";
        }
    }

    //报修人查询报修记录
    @RequestMapping(value = "/selectRecord",method = RequestMethod.POST)
    public Object selectRecord(HttpServletRequest request, @RequestBody String ufrepair) {
        try {
            return ufrepairService.selectRecord(ufrepair);
        }catch (Exception e){
            e.printStackTrace();
            return "报修人查询报修记录失败";
        }
    }

    //管理员维修管理
    @RequestMapping(value = "/management",method = RequestMethod.POST)
    public Object management(HttpServletRequest request, @RequestBody String ufrepair) {
        try {
            return ufrepairService.management(ufrepair);
        }catch (Exception e){
            e.printStackTrace();
            return "管理员维修管理失败";
        }
    }

    //管理员派单
    @RequestMapping(value = "/sendOrders",method = RequestMethod.POST)
    public Object sendOrders(HttpServletRequest request, @RequestBody Ufrepair ufrepair) {
        try {
            return ufrepairService.sendOrders(ufrepair);
        }catch (Exception e){
            e.printStackTrace();
            return "派单失败";
        }
    }

    //完成维修
    @RequestMapping(value = "/finishWx",method = RequestMethod.POST)
    public Object finishWx(HttpServletRequest request, @RequestBody Ufrepair ufrepair) {
        try {
            return ufrepairService.finishWx(ufrepair);
        }catch (Exception e){
            e.printStackTrace();
            return "完成维修失败";
        }
    }

    //报修评价
    @RequestMapping(value = "/repairEvaluate",method = RequestMethod.POST)
    public Object repairEvaluate(HttpServletRequest request, @RequestBody Ufrepair ufrepair) {
        try {
            return ufrepairService.repairEvaluate(ufrepair);
        }catch (Exception e){
            e.printStackTrace();
            return "报修评价失败";
        }
    }

    //页面数据展示
    @RequestMapping(value = "/getData",method = RequestMethod.POST)
    public Object getData(HttpServletRequest request) {
        try {
            return ufrepairService.getData();
        }catch (Exception e){
            e.printStackTrace();
            return "页面数据展示失败";
        }
    }

    //获取下拉框数据
    @RequestMapping(value = "/dropDownBox",method = RequestMethod.GET)
    public Object dropDownBox(HttpServletRequest request) {
        try {
            return ufrepairService.dropDownBox();
        }catch (Exception e){
            e.printStackTrace();
            return "获取下拉框数据失败";
        }
    }

    //通过id查询设备信息
    @RequestMapping(value = "/getEquipmentById",method = RequestMethod.POST)
    public Object getEquipmentById(@RequestBody String id) {
        try {
            return ufrepairService.getEquipmentById(id);
        }catch (Exception e){
            e.printStackTrace();
            return "通过id查询设备信息失败";
        }
    }
}
