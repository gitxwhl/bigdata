package com.property.controller;

import com.property.entity.Wyequipmentstrategy;
import com.property.service.WyequipmentstrategyService;
import com.property.utils.PageBean;
import com.property.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/StEquipmentstrategy.do")
@Slf4j
@CrossOrigin
public class WyequipmentstrategyController {
    @Autowired
    WyequipmentstrategyService wyequipmentstrategyService;

    /**
     * 查询维保策略
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/getEquipmentStrategy",method = RequestMethod.POST)
    public PageBean getEquipmentStrategy(HttpServletRequest request, @RequestBody String param){
        return wyequipmentstrategyService.getEquipmentStrategy(param);
    }

    /**
     * 添加维保策略
     * @param request
     * @return
     */
    @RequestMapping(value = "/insertEquipmentStrategy",method = RequestMethod.POST)
    private Result insertEquipmentStrategy(HttpServletRequest request, @RequestBody String param){
        Result result = new Result();
        try {
            wyequipmentstrategyService.insertEquipmentStrategy(param);
            result.setMessage("添加成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("添加失败");
        }
        return result;
    }

    /**
     * 查询修改维保策略
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/getEquipmentStrategyById",method = RequestMethod.POST)
    public Map getEquipmentStrategyById(HttpServletRequest request,@RequestBody String param){
        return wyequipmentstrategyService.getEquipmentStrategyById(param);
    }

    /**
     * 修改维保策略
     * @param request
     * @param wyequipmentstrategy
     * @return
     */
    @RequestMapping(value = "/updateEquipmentStrategy",method = RequestMethod.POST)
    public Result updateEquipmentStrategy(HttpServletRequest request, @RequestBody Wyequipmentstrategy wyequipmentstrategy){
        Result result = new Result();
        try {
            wyequipmentstrategyService.updateEquipmentStrategy(wyequipmentstrategy);
            result.setMessage("修改成功");
        }catch (Exception e){
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 删除维保策略
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/deleteEquipmentStrategy",method = RequestMethod.POST)
    public Result deleteEquipmentStrategy(HttpServletRequest request, @RequestBody String param){
        Result result = new Result();
        try {
            wyequipmentstrategyService.deleteEquipmentStrategy(param);
            result.setMessage("删除成功");
        }catch (Exception e){
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 查询策略分类
     * @param request
     * @return
     */
    @RequestMapping(value = "/getPolicy",method = RequestMethod.POST)
    public Object getPolicy(HttpServletRequest request){
        try {
            return wyequipmentstrategyService.getPolicy();
        }catch (Exception e){
            e.printStackTrace();
            return "获取失败";
        }
    }

}
