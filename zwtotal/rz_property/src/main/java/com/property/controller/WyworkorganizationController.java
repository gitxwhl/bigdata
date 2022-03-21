package com.property.controller;

import com.property.entity.Wyregister;
import com.property.entity.Wyworkorganization;
import com.property.service.WyworkorganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/Wyworkorganization.do")
@Slf4j
@CrossOrigin
public class WyworkorganizationController {

    @Autowired
    private WyworkorganizationService workorganizationService;

    //工作安排列表
    @RequestMapping(value = "/getJobPlacement",method = RequestMethod.POST)
    public Object getJobPlacement(HttpServletRequest request, @RequestBody String param) {
        try {
            return workorganizationService.getJobPlacement(param);
        }catch (Exception e){
            e.printStackTrace();
            return "获取工作安排列表失败";
        }
    }

    //新增工作安排
    @RequestMapping(value = "/addJobPlacement",method = RequestMethod.POST)
    public Object addJobPlacement(HttpServletRequest request, @RequestBody String param) {
        try {
            return workorganizationService.addJobPlacement(param);
        }catch (Exception e){
            e.printStackTrace();
            return "新增工作安排失败";
        }
    }

    //修改工作安排
    @RequestMapping(value = "/updateJobPlacement",method = RequestMethod.POST)
    public Object updateJobPlacement(HttpServletRequest request, @RequestBody Wyworkorganization param) {
        try {
            return workorganizationService.updateJobPlacement(param);
        }catch (Exception e){
            e.printStackTrace();
            return "修改工作安排失败";
        }
    }

    //删除工作安排
    @RequestMapping(value = "/deleteJobPlacement",method = RequestMethod.POST)
    public Object deleteJobPlacement(HttpServletRequest request, @RequestBody Wyworkorganization param) {
        try {
            return workorganizationService.deleteJobPlacement(param);
        }catch (Exception e){
            e.printStackTrace();
            return "删除工作安排失败";
        }
    }

    //工作提醒
    @RequestMapping(value = "/reminderWork",method = RequestMethod.GET)
    public Object reminderWork(HttpServletRequest request) {
        try {
            return workorganizationService.reminderWork();
        }catch (Exception e){
            e.printStackTrace();
            return "工作提醒异常";
        }
    }

    //登记植被种植情况
    @RequestMapping(value = "/addRegisterZz",method = RequestMethod.POST)
    public Object addRegisterZz(HttpServletRequest request, @RequestBody String param) {
        try {
            return workorganizationService.addRegisterZz(param);
        }catch (Exception e){
            e.printStackTrace();
            return "添加登记失败";
        }
    }

    //登记植被修剪情况
    @RequestMapping(value = "/addRegisterXj",method = RequestMethod.POST)
    public Object addRegisterXj(HttpServletRequest request, @RequestBody String param) {
        try {
            return workorganizationService.addRegisterXj(param);
        }catch (Exception e){
            e.printStackTrace();
            return "添加登记失败";
        }
    }

    //空间情况
    @RequestMapping(value = "/getSpaceInfo",method = RequestMethod.POST)
    public Object getSpaceInfo(HttpServletRequest request,@RequestBody String param) {
        try {
            return workorganizationService.getSpaceInfo(param);
        }catch (Exception e){
            e.printStackTrace();
            return "获取空间情况失败";
        }
    }

    //获取下拉框数据
    @RequestMapping(value = "/dropDownBox",method = RequestMethod.GET)
    public Object dropDownBox(HttpServletRequest request) {
        try {
            return workorganizationService.dropDownBox();
        }catch (Exception e){
            e.printStackTrace();
            return "获取下拉框数据失败";
        }
    }
}
