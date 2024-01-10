package com.mstdemo.mst.controller;

import com.mstdemo.mst.exception.CommonResult;
import com.mstdemo.mst.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("TareaController.do")
public class TareaController {
    @Autowired
    private TareaService tareaService;


    @PostMapping("/findTrea")
    public CommonResult findTrea(){
        return new  CommonResult(200,"查询成功",tareaService.findTare());
    }
    @PostMapping("/findTeraMap")
    public CommonResult findTeraMap(){
        return new CommonResult(200,"查询成功",tareaService.findTareaMap());
    }





}
