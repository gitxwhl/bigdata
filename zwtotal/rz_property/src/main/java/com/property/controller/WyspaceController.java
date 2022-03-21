package com.property.controller;

import com.property.entity.Wyspace;
import com.property.service.WyspaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/Wyspace.do")
@Slf4j
@CrossOrigin
public class WyspaceController {

    @Autowired
    WyspaceService wyspaceService;

    //保洁区域列表
    @RequestMapping(value = "/getCleanSpace",method = RequestMethod.POST)
    public Object getCleanSpace(HttpServletRequest request, @RequestBody String param) {
        try {
            return wyspaceService.getCleanSpace(param);
        }catch (Exception e){
            e.printStackTrace();
            return "获取保洁区域列表失败";
        }
    }

    //查看附件
    @RequestMapping(value = "/getFiles",method = RequestMethod.POST)
    public Object getFiles(HttpServletRequest request, @RequestBody String param) {
        try {
            return wyspaceService.getFiles(param);
        }catch (Exception e){
            e.printStackTrace();
            return "查看附件失败";
        }
    }

    //新增保洁区域
    @RequestMapping(value = "/addCleanSpace",method = RequestMethod.POST)
    public Object addCleanSpace(HttpServletRequest request, @RequestBody String param) {
        try {
            return wyspaceService.addCleanSpace(param);
        }catch (Exception e){
            e.printStackTrace();
            return "新增保洁区域失败";
        }
    }

    //修改保洁区域
    @RequestMapping(value = "/updateCleanSpace",method = RequestMethod.POST)
    public Object updateCleanSpace(HttpServletRequest request, @RequestBody String param) {
        try {
            return wyspaceService.updateCleanSpace(param);
        }catch (Exception e){
            e.printStackTrace();
            return "修改保洁区域失败";
        }
    }

    //删除保洁区域
    @RequestMapping(value = "/deleteCleanSpace",method = RequestMethod.POST)
    public Object deleteCleanSpace(HttpServletRequest request, @RequestBody Wyspace param) {
        try {
            return wyspaceService.deleteCleanSpace(param);
        }catch (Exception e){
            e.printStackTrace();
            return "删除保洁区域失败";
        }
    }

    //获取下拉框数据
    @RequestMapping(value = "/dropDownBox",method = RequestMethod.GET)
    public Object dropDownBox(HttpServletRequest request) {
        try {
            return wyspaceService.dropDownBox();
        }catch (Exception e){
            e.printStackTrace();
            return "获取下拉框数据失败";
        }
    }
}
