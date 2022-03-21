package com.property.controller;

import com.property.service.WyToolsconsumablesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/wyToolsconsumables.do")
@Slf4j
@CrossOrigin
public class WyToolsconsumablesController {

    @Autowired
    private WyToolsconsumablesService wyToolsconsumablesService;


    //保洁工具消耗管理列表
    @RequestMapping(value = "/getToolConsumption",method = RequestMethod.POST)
    public Object getPlanDetails(HttpServletRequest request, @RequestBody String param) {
        try {
            return wyToolsconsumablesService.getToolConsumption(param);
        }catch (Exception e){
            e.printStackTrace();
            return "展示保洁工具消耗管理列表失败";
        }
    }
    //新增材料及人员信息
    @RequestMapping(value = "/insertMaterialsStaff",method = RequestMethod.POST)
    public Object insertMaterialsStaff(HttpServletRequest request, @RequestBody String param) {
        try {
            return wyToolsconsumablesService.insertMaterialsStaff(param);
        }catch (Exception e){
            e.printStackTrace();
            return "新增数据失败";
        }
    }

    //修改材料及人员信息
    @RequestMapping(value = "/updateMaterialsStaff",method = RequestMethod.POST)
    public Object updateMaterialsStaff(HttpServletRequest request, @RequestBody String param) {
        try {
            return wyToolsconsumablesService.updateMaterialsStaff(param);
        }catch (Exception e){
            e.printStackTrace();
            return "修改数据失败";
        }
    }


    //删除数据
    @RequestMapping(value = "/deleteMaterialsStaf",method = RequestMethod.POST)
    public Object deleteMaterialsStaf(HttpServletRequest request, @RequestBody String param) {
        try {
            return wyToolsconsumablesService.deleteMaterialsStaf(param);
        }catch (Exception e){
            e.printStackTrace();
            return "删除数据失败";
        }
    }
    //数据详情
    @RequestMapping(value = "/insertDetails",method = RequestMethod.POST)
    public Object insertDetails(HttpServletRequest request, @RequestBody String param) {
        try {
            return wyToolsconsumablesService.insertDetails(param);
        }catch (Exception e){
            e.printStackTrace();
            return "删除数据失败";
        }
    }
    //文件导出
    @RequestMapping(value = "/fileImport",method = RequestMethod.POST)
    public Object fileImport(HttpServletRequest request, HttpServletResponse response) {
        try {
            return wyToolsconsumablesService.fileImport(request,response);
        }catch (Exception e){
            e.printStackTrace();
            return "文件导出失败";
        }
    }
    //文件导入
    @RequestMapping(value = "/fileExample",method = RequestMethod.POST)
    public Object fileExample(HttpServletRequest request, HttpServletResponse response) {
        try {
            return wyToolsconsumablesService.fileExample(request,response);
        }catch (Exception e){
            e.printStackTrace();
            return "文件导入失败";
        }
    }

}
