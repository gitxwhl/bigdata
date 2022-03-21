package com.canteen.controller;


import com.alibaba.fastjson.JSONObject;
import com.canteen.entity.StMydishes;
import com.canteen.entity.StfoodManagement;
import com.canteen.service.StMyfoodService;
import com.canteen.utils.PageBean;
import com.canteen.utils.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/StMyfoodController.do")
@Slf4j
@CrossOrigin
public class StMyfoodController {
    @Autowired
    private StMyfoodService stMyfoodService;

    /**
     * 获取不同运维餐厅下所有的菜品列表
     * @param request
     * @param param
     * @return
     */

    @RequestMapping(value = "/selectByRestaurant", method = RequestMethod.POST)
    public PageBean selectByRestaurant(HttpServletRequest request, @RequestBody String param) {
        return stMyfoodService.selectByRestaurant(param);
    }

    /**
     * 在全量库中根据菜品名称查询菜品信息
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/selectNameByFoodManage", method = RequestMethod.POST)
    public PageBean selectNameByFoodManage(HttpServletRequest request, @RequestBody String param) {
        return stMyfoodService.selectNameByFoodManage(param);
    }

    /**
     * 菜品营养分析
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/CalculationNutritional", method = RequestMethod.POST)
    public ResultMessage CalculationNutritional(HttpServletRequest request, @RequestBody String param) {
        ResultMessage resultMessage = new ResultMessage();
        JSONObject paramDate = JSONObject.parseObject(param);
        String id = paramDate.getString("dishcode");
        resultMessage.setData(stMyfoodService.CalculationNutritional(id));
      return resultMessage;

    }

    /**
     * 根据菜品id删除菜品
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/deleteMyFoodById", method = RequestMethod.POST)
    public ResultMessage deleteMyFoodById(HttpServletRequest request, @RequestBody String param) {
        JSONObject paramDate = JSONObject.parseObject(param);
        int id = paramDate.getIntValue("id");
        ResultMessage resultMessage = new ResultMessage();
        try {
            stMyfoodService.deleteMyFoodById(id);
            resultMessage.setMessage("删除成功！");
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultMessage;
    }

    /**
     * 添加菜品信息
     * @param request
     * @param
     * @return
     */

    @RequestMapping(value = "/insertMyFood", method = RequestMethod.POST)
    public ResultMessage insertMyFood(HttpServletRequest request, @RequestBody String param) {
        ResultMessage resultMessage = new ResultMessage();
        try {
            stMyfoodService.insertMyFood(param);
            resultMessage.setMessage("添加菜品成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultMessage;
    }

    /**
     * 菜类查询
     * @param request
     * @return
     */
    @RequestMapping(value = "/selectdish",method = RequestMethod.GET)
    private List selectdish(HttpServletRequest request){
        return stMyfoodService.selectdish();
    }


    //---------------------------------------------查询菜品-------------------------------------------------
    //查询菜品
    @RequestMapping(value = "/queryDish", method = RequestMethod.POST)
    public Object queryDish(HttpServletRequest request, @RequestBody String param) {
        try {
            ResultMessage resultMessage = new ResultMessage();
            resultMessage.setData(stMyfoodService.queryDish(param));
            return resultMessage;
        }catch (Exception e){
            e.printStackTrace();
            return "查询菜品异常";
        }
    }

    //菜品营养成分分析
    @RequestMapping(value = "/getNutritional", method = RequestMethod.POST)
    public Object getNutritional(HttpServletRequest request, @RequestBody String param) {
        try {
            return stMyfoodService.getNutritional(param);
        }catch (Exception e){
            e.printStackTrace();
            return "获取菜品营养成分分析失败";
        }
    }

    //获取字典表定义
    @RequestMapping(value = "/queryDishDic", method = RequestMethod.GET)
    private Object queryDishDic(HttpServletRequest request){
        return stMyfoodService.queryDishDic();
    }
}

