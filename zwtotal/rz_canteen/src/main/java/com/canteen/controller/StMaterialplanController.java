package com.canteen.controller;

import com.canteen.service.StMaterialplanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/StMaterialplan.do")
@Slf4j
@CrossOrigin
public class StMaterialplanController {

    @Autowired
    private StMaterialplanService stMaterialplanService;

    //原料需求查询
    @RequestMapping(value = "/getMaterialPlan",method = RequestMethod.POST)
    public Object getMaterialPlan(HttpServletRequest request, @RequestBody String param){
        try {
            return stMaterialplanService.getMaterialPlan(param);
        }catch (Exception e){
            e.printStackTrace();
            return "获取列表失败";
        }
    }

    //原料需求导出
    @RequestMapping(value = "/exportMaterialPlan", method = RequestMethod.POST)
    private Object exportMaterialPlan(HttpServletRequest request, HttpServletResponse response, @RequestBody String param){
        try {
            return stMaterialplanService.exportMaterialPlan(request,response,param);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "原料需求导出失败";
        }
    }
}
