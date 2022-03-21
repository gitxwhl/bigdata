package com.property.controller;


import com.property.service.WyequipmentinspectionService;
import com.property.utils.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/StEquipmentinspection.do")
@Slf4j
@CrossOrigin
public class WyequipmentinspectionController {
    @Autowired
    WyequipmentinspectionService stEquipmentInspectionService;

    /**
     * 获取设备巡检信息
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/selectEquipmentInspection",method = RequestMethod.POST)
    public PageBean selectEquipmentInspection(HttpServletRequest request, @RequestBody String param){
        return stEquipmentInspectionService.selectEquipmentInspection(param);
    }

    /**
     * 添加巡检计划信息
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/insertEquipmentInspection",method = RequestMethod.POST)
    public Object insertEquipmentInspection(HttpServletRequest request, @RequestBody String param){
        try{
            return stEquipmentInspectionService.insertEquipmentInspection(param);
        }catch (Exception e){
            e.printStackTrace();
            return "添加失败";
        }
    }

    /**
     * 添加巡检记录信息
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateEquipmentInspection",method = RequestMethod.POST)
    public Object updateEquipmentInspection(HttpServletRequest request, @RequestBody String param){
        try{
            return stEquipmentInspectionService.updateEquipmentInspection(param);
        }catch (Exception e){
            e.printStackTrace();
            return "添加失败";
        }
    }

    /**
     * 获取线路名称，涉及设备，巡检结果
     * @param request
     * @return
     */
    @RequestMapping(value = "/getInspectionAndResults",method = RequestMethod.POST)
    public Object getInspection(HttpServletRequest request){
        try {
            return stEquipmentInspectionService.getInspectionAndResults();
        }catch (Exception e){
            e.printStackTrace();
            return "获取信息失败";
        }
    }
}
