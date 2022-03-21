package com.property.controller;


import com.property.service.WyaccidentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/Wyaccident.do")
@Slf4j
@CrossOrigin
public class WyaccidentController {

    @Autowired
    private WyaccidentService wyaccidentService;

    //添加事故记录
    @RequestMapping(value = "/addAccident",method = RequestMethod.POST)
    public Object addAccident(HttpServletRequest request, @RequestBody String wydutyrecord) {
        try {
            return wyaccidentService.addAccident(wydutyrecord);
        }catch (Exception e){
            e.printStackTrace();
            return "添加事故记录失败";
        }
    }

    //查询事故记录
    @RequestMapping(value = "/getAccident",method = RequestMethod.POST)
    public Object getAccident(HttpServletRequest request, @RequestBody String wydutyrecord) {
        try {
            return wyaccidentService.getAccident(wydutyrecord);
        }catch (Exception e){
            e.printStackTrace();
            return "查询事故记录失败";
        }
    }

    //获取下拉框数据
    @RequestMapping(value = "/dropDownBox",method = RequestMethod.GET)
    public Object dropDownBox(HttpServletRequest request) {
        try {
            return wyaccidentService.dropDownBox();
        }catch (Exception e){
            e.printStackTrace();
            return "获取下拉框数据失败";
        }
    }
}
