package com.officeServices.controller;

import com.officeServices.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/Department.do")
@Slf4j
@CrossOrigin
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 部门导航栏
     */
    @RequestMapping(value = "/getDepartmentList", method = RequestMethod.GET)
    public Object getDepartmentList(){
        try {
            return departmentService.getDepartmentList();
        }catch (Exception e){
            e.printStackTrace();
            return "获取导航栏失败";
        }
    }
}
