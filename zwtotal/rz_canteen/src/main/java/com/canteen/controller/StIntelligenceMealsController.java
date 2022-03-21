package com.canteen.controller;

import com.alibaba.fastjson.JSONObject;
import com.canteen.service.StIntelligenceMealService;
import com.canteen.utils.Result;
import com.canteen.utils.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/StIntelligenceMeals.do")
@Slf4j
@CrossOrigin
public class StIntelligenceMealsController {

    @Autowired
    StIntelligenceMealService stIntelligenceMealService;


    /**
     * 手动添加--菜品查询
     *
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/SelectMyFood", method = RequestMethod.POST)
    private List SelectMyFood(HttpServletRequest request, @RequestBody String param) {
        return stIntelligenceMealService.SelectMyFood(param);
    }

    /**
     * 菜品添加
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/UpdateDishescategory", method = RequestMethod.POST)
    private ResultMessage UpdateDishescategory(HttpServletRequest request,@RequestBody String param ) {
        JSONObject paramDate = JSONObject.parseObject(param);
        List ids = paramDate.getJSONArray("ids");
        ResultMessage resultMessage = new ResultMessage();
        if(ids.size() != 0) {
           stIntelligenceMealService.UpdateDishescategory(ids);
            resultMessage.setMessage("添加菜品成功");
        }
        else {
            resultMessage.setMessage("添加菜品失败");
    }
        return resultMessage;
    }

    /**
     * 一键排餐
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/SelectMyFoodByRestaurant",method = RequestMethod.POST)
    private Result SelectMyFoodByRestaurant(HttpServletRequest request,@RequestBody String param ){
        JSONObject paramDate = JSONObject.parseObject(param);
        String restaurant = paramDate.getString("restaurant");
        Result result = new Result();
        try {
            stIntelligenceMealService.SelectMyFoodByRestaurant(restaurant);
            result.setMsg("一键排餐成功");
        }catch (Exception e){
            result.setMsg(e.getMessage());
        }
         return result;
    }

    /**
     * 删除菜品
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/DeleteMyFoodByID",method = RequestMethod.POST)
    private String DeleteMyFoodByID(HttpServletRequest request,@RequestBody String param ){
        JSONObject paramDate = JSONObject.parseObject(param);
        int id = paramDate.getIntValue("id");
        stIntelligenceMealService.DeleteMyFoodByID(id);
        return "200";
    }

    /**
     * 一键重排
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/SelectMyFoodByRepeat",method = RequestMethod.POST)
    private Result SelectMyFoodByRepeat(HttpServletRequest request, @RequestBody String param ){
        JSONObject paramDate = JSONObject.parseObject(param);
        String restaurant = paramDate.getString("restaurant");
        String meals = paramDate.getString("meals");
        Result result = new Result();
        try {
            stIntelligenceMealService.SelectMyFoodByRepeat(restaurant,meals);
            result.setMsg("重排成功");
        }catch (Exception e){
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 菜品替换
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/dishesReplacement" ,method = RequestMethod.POST)
    private Result dishesReplacement(HttpServletRequest request,@RequestBody String param){
        Result result = new Result();
        try {
            stIntelligenceMealService.dishesReplacement(param);
            result.setMsg("菜品替换成功");
        }catch (Exception e){
            result.setMsg("菜品替换失败");
        }
        return result;
    }
    /**
     * 一键导出排餐列表
     * @param request
     * @param response
     * @param param
     * @return
     */
    @RequestMapping(value = "/choiceDishExport", method = RequestMethod.POST)
    private Object choiceDishExport(HttpServletRequest request, HttpServletResponse response, @RequestBody String param) throws Exception{
        try {
            return stIntelligenceMealService.choiceDishExport(request,response,param);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "导出排菜列表失败";
    }

    /**
     * 进入页面的查询
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/SelectByRestaurant",method = RequestMethod.POST)
    private Map SelectByRestaurant(HttpServletRequest request,@RequestBody String param ){
        JSONObject paramDate = JSONObject.parseObject(param);
        String restaurant = paramDate.getString("restaurant");
        String date = paramDate.getString("date");
        return stIntelligenceMealService.SelectByRestaurant(restaurant,date);
    }

}
