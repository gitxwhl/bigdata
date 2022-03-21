package com.canteen.service;

import com.canteen.utils.PageBean;

import java.util.Map;

public interface StAccurateChoosingMealsService {
    //获取营养选餐列表
    PageBean getNutritionList(String paramJson);

    //餐厅及种类
    Map getDiningRoom();
    //删除
    String deleteNutrition(String param);
    //添加营养
    Object addNutrition(String paramJson);
    //自动打菜 修改状态
    String updateState(String param);

}
