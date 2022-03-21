package com.canteen.service;
import com.canteen.entity.StIntelligenceMealsVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface StIntelligenceMealService {

    //手动添加-菜品查询
   List SelectMyFood(String param);

    //菜品添加
    void UpdateDishescategory(List<Integer> ids);

    //一键排餐
    void SelectMyFoodByRestaurant(String restaurant);

    //删除菜品
   void DeleteMyFoodByID(Integer id);

    //一键重排
    void SelectMyFoodByRepeat(String restaurant,String meals);

    //菜品替换
    void dishesReplacement(String param);

    //一键导出排餐列表
    Object choiceDishExport(HttpServletRequest request, HttpServletResponse response, String param) throws UnsupportedEncodingException, Exception;

    //初始页面查询
    Map SelectByRestaurant(String restaurant, String date);

}
