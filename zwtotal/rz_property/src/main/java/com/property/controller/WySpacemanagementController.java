package com.property.controller;

import com.property.service.WySpacemanagementListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/WySpacemanagement.do")
@Slf4j
@CrossOrigin
public class WySpacemanagementController {

    @Autowired
   private WySpacemanagementListService wySpacemanagementListService;



    //供应商管列表
    @RequestMapping(value = "/WySpacemanagementList",method = RequestMethod.POST)
    public Object WySpacemanagementList(HttpServletRequest request, @RequestBody String param) {
        try {
            return wySpacemanagementListService.WySpacemanagementList(param);
        }catch (Exception e){
            e.printStackTrace();
            return "供应商管列表失败";
        }
    }


    //植被列表
    @RequestMapping(value = "/WyvegetationtList",method = RequestMethod.POST)
    public Object WyvegetationtList(HttpServletRequest request, @RequestBody String param) {
        try {
            return wySpacemanagementListService.WyvegetationtList(param);
        }catch (Exception e){
            e.printStackTrace();
            return "植被列表失败";
        }
    }

    //新增供应商管理及植被
    @RequestMapping(value = "/getSupplierManagement",method = RequestMethod.POST)
    public Object getWycorporateList(HttpServletRequest request, @RequestBody String param) {
        try {
            return wySpacemanagementListService.getSupplierManagement(param);
        }catch (Exception e){
            e.printStackTrace();
            return "新增供应商管理及植被失败";
        }
    }

}