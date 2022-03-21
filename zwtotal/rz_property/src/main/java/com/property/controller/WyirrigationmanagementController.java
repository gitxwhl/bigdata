package com.property.controller;


import com.property.service.WyirrigationmanagementService;
import com.property.utils.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/Wyirrigationmanagement.do")
@Slf4j
@CrossOrigin
public class WyirrigationmanagementController {

    @Autowired
    private WyirrigationmanagementService wyirrigationmanagementService;


    //返现灌溉方式
    @RequestMapping(value = "/getIrrigationMethods",method = RequestMethod.GET)
    public Object getIrrigationMethods(HttpServletRequest request) {
        try {
            return wyirrigationmanagementService.getIrrigationMethods();
        }catch (Exception e){
            e.printStackTrace();
            return "返现灌溉方式失败";
        }
    }


    //新增计划
    @RequestMapping(value = "/getNewlyIncreasedPlan",method = RequestMethod.POST)
    public Object getWycorporateList(HttpServletRequest request, @RequestBody String param) {
        try {
            return wyirrigationmanagementService.getNewlyIncreasedPlan(param);
        }catch (Exception e){
            e.printStackTrace();
            return "新增计划失败";
        }
    }

    //修改计划
    @RequestMapping(value = "/getamendPlan",method = RequestMethod.POST)
    public Object getamendPlan(HttpServletRequest request, @RequestBody String param) {
        try {
            return wyirrigationmanagementService.getamendPlan(param);
        }catch (Exception e){
            e.printStackTrace();
            return "修改计划失败";
        }
    }


    //供应商计划列表
    @RequestMapping(value ="/WyirrigationmanagementList",method = RequestMethod.POST)
    public PageBean WyirrigationmanagementList(HttpServletRequest request, @RequestBody String param) {
        return wyirrigationmanagementService.WyirrigationmanagementList(param);

    }

    //计划评价 详情接口
    @RequestMapping(value = "/getPlanDetails",method = RequestMethod.POST)
    public Object getPlanDetails(HttpServletRequest request, @RequestBody String param) {
        try {
            return wyirrigationmanagementService.getPlanDetails(param);
        }catch (Exception e){
            e.printStackTrace();
            return "展示计划详情失败";
        }
    }
    //计划评价 评估打分更新接口
    @RequestMapping(value = "/getAssessRedact",method = RequestMethod.POST)
    public Object getAssess(HttpServletRequest request, @RequestBody String param) {
        try {
            return wyirrigationmanagementService.getAssessRedact(param);
        }catch (Exception e){
            e.printStackTrace();
            return "计划评价 评估打分失败";
        }
    }

    //计划评价 评估打分上传附件
    @RequestMapping(value = "/getuploading",method = RequestMethod.POST)
    public Object getuploading(HttpServletRequest request, @RequestBody String param) {
        try {
            return wyirrigationmanagementService.getuploading(param);
        }catch (Exception e){
            e.printStackTrace();
            return "评估打分上传附件失败";
        }
    }

    //计划及检查情况图
    @RequestMapping(value = "/getIrrigateExamine",method = RequestMethod.POST)
    public Object getIrrigateExamine(HttpServletRequest request, @RequestBody String param) {
        try {
            return wyirrigationmanagementService.getIrrigateExamine(param);
        }catch (Exception e){
            e.printStackTrace();
            return "展示计划及检查情况图失败";
        }
    }
}
