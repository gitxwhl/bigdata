package com.property.controller;

import com.property.service.WypersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Wyperson.do")
@Slf4j
@CrossOrigin
public class WypersonController {

    @Autowired
    private WypersonService wypersonService;


    //-----------------------------------------------排班计划-----------------------------------------------------
    //查询班次明细
    @RequestMapping(value = "/getFrequency",method = RequestMethod.POST)
    public Object getFrequency(@RequestBody String param){
        try {
            return wypersonService.getFrequency(param);
        }catch (Exception e){
            e.printStackTrace();
            return "查询班次明细失败";
        }
    }

    //添加班次明细
    @RequestMapping(value = "/addFrequency",method = RequestMethod.POST)
    public Object addFrequency(@RequestBody String param){
        try {
            return wypersonService.addFrequency(param);
        }catch (Exception e){
            e.printStackTrace();
            return "添加班次明细失败";
        }
    }

    //修改班次明细
    @RequestMapping(value = "/updateFrequency",method = RequestMethod.POST)
    public Object updateFrequency(@RequestBody String param){
        try {
            return wypersonService.updateFrequency(param);
        }catch (Exception e){
            e.printStackTrace();
            return "修改班次明细失败";
        }
    }

    //-----------------------------------------------巡检计划-----------------------------------------------------
    //巡检计划列表
    @RequestMapping(value = "/inspectionList",method = RequestMethod.POST)
    public Object inspectionList(@RequestBody String param){
        try {
            return wypersonService.inspectionList(param);
        }catch (Exception e){
            e.printStackTrace();
            return "获取巡检计划列表失败";
        }
    }

    //新增巡检计划
    @RequestMapping(value = "/addInspection",method = RequestMethod.POST)
    public Object addInspection(@RequestBody String param){
        try {
            return wypersonService.addInspection(param);
        }catch (Exception e){
            e.printStackTrace();
            return "新增失败";
        }
    }

    //修改巡检计划
    @RequestMapping(value = "/updateInspection",method = RequestMethod.POST)
    public Object updateInspection(@RequestBody String param){
        try {
            return wypersonService.updateInspection(param);
        }catch (Exception e){
            e.printStackTrace();
            return "修改失败";
        }
    }

    //删除巡检计划
    @RequestMapping(value = "/deleteInspection",method = RequestMethod.POST)
    public Object deleteInspection(@RequestBody String param){
        try {
            return wypersonService.deleteInspection(param);
        }catch (Exception e){
            e.printStackTrace();
            return "删除失败";
        }
    }

    //下拉框
    @RequestMapping(value = "/dropDownBox",method = RequestMethod.GET)
    public Object dropDownBox(){
        try {
            return wypersonService.dropDownBox();
        }catch (Exception e){
            e.printStackTrace();
            return "获取下拉框失败";
        }
    }

    //-----------------------------------------------岗位资料-----------------------------------------------------
    //新增岗位资料
    @RequestMapping(value = "/insertWyPerson",method = RequestMethod.POST)
    public Object insertWyPerson(@RequestBody String param){
        try {
            return wypersonService.insertWyPerson(param);
        }catch (Exception e){
            e.printStackTrace();
            return "新增失败";
        }
    }

    //秩维计划  岗位列表
    @RequestMapping(value = "/wypersonList",method = RequestMethod.POST)
    public Object wypersonList(@RequestBody String param){
        try {
            return wypersonService.wypersonList(param);
        }catch (Exception e){
            e.printStackTrace();
            return "获取岗位列表失败";
        }
    }
}
