package com.property.controller;

import com.property.entity.Wydutyrecord;
import com.property.service.WydutyrecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/Wydutyrecord.do")
@Slf4j
@CrossOrigin
public class WydutyrecordController {

    @Autowired
    private WydutyrecordService wydutyrecordService;

    //添加值班记录
    @RequestMapping(value = "/addDutyRecord",method = RequestMethod.POST)
    public Object addDutyRecord(HttpServletRequest request, @RequestBody Wydutyrecord wydutyrecord) {
        try {
            return wydutyrecordService.addDutyRecord(wydutyrecord);
        }catch (Exception e){
            e.printStackTrace();
            return "添加值班记录失败";
        }
    }

    //查询值班记录
    @RequestMapping(value = "/getDutyRecord",method = RequestMethod.POST)
    public Object getDutyRecord(HttpServletRequest request, @RequestBody String wydutyrecord) {
        try {
            return wydutyrecordService.getDutyRecord(wydutyrecord);
        }catch (Exception e){
            e.printStackTrace();
            return "查询值班记录失败";
        }
    }

    //获取下拉框数据
    @RequestMapping(value = "/dropDownBox",method = RequestMethod.GET)
    public Object dropDownBox(HttpServletRequest request) {
        try {
            return wydutyrecordService.dropDownBox();
        }catch (Exception e){
            e.printStackTrace();
            return "获取下拉框数据失败";
        }
    }
}
