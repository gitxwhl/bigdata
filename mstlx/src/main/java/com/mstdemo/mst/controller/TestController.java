package com.mstdemo.mst.controller;

import com.mstdemo.mst.bean.DeptInfo;
import com.mstdemo.mst.service.DeptInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
   private DeptInfoService deptInfoService;


    @RequestMapping("/add")
    public int add(@RequestBody DeptInfo deptInfo){
        return deptInfoService.addDept(deptInfo);
    }
}
