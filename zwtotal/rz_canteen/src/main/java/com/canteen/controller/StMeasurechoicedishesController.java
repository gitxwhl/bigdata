package com.canteen.controller;

import com.canteen.service.StMeasurechoicedishesService;
import com.canteen.utils.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@RestController
@RequestMapping("/StMeasurechoicedishes.do")
@Slf4j
@CrossOrigin
public class StMeasurechoicedishesController {

    @Autowired
    private StMeasurechoicedishesService measurechoicedishesService;

    //获取排菜列表
    @RequestMapping(value = "/choiceDishList", method = RequestMethod.POST)
    private PageBean choiceDishList(HttpServletRequest request, @RequestBody String param){
        return measurechoicedishesService.choiceDishList(param);
    }

    //删除排菜
    @RequestMapping(value = "/deleteChoiceDish", method = RequestMethod.POST)
    private String deleteChoiceDish(HttpServletRequest request, @RequestBody String param){
        try {
            return measurechoicedishesService.deleteChoiceDish(param);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "删除排菜失败";
    }

    //查询可关联属性
    @RequestMapping("/getRelevance")
    public Map getRelevance(){
        return measurechoicedishesService.getRelevance();
    }

    //新增排菜
    @RequestMapping(value = "/insertChoiceDish", method = RequestMethod.POST)
    private String insertChoiceDish(HttpServletRequest request, @RequestBody String param){
        try {
            return measurechoicedishesService.insertChoiceDish(param);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "新增排菜失败";
    }

    //修改排菜
    @RequestMapping(value = "/updateChoiceDish", method = RequestMethod.POST)
    private String updateChoiceDish(HttpServletRequest request, @RequestBody String param){
        try {
            return measurechoicedishesService.updateChoiceDish(param);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "修改排菜失败";
    }

    //导出排菜列表
    @RequestMapping(value = "/choiceDishExport", method = RequestMethod.POST)
    private Object choiceDishExport(HttpServletRequest request, HttpServletResponse response, @RequestBody String param){
        try {
            return measurechoicedishesService.choiceDishExport(request,response,param);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "导出排菜列表失败";
    }
    //获取导入文件名
    @RequestMapping(value = "/getfileName", method = RequestMethod.POST)
    private Object getfileName(){
        try {
            return measurechoicedishesService.getfileName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "获取导入文件名";
    }

    //导入排菜列表
    @RequestMapping(value = "/choiceDishImport", method = RequestMethod.POST)
    private Object choiceDishImport(HttpServletRequest request, HttpServletResponse response){
        try {
            return measurechoicedishesService.choiceDishImport(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "导入排菜列表失败";
    }

}
