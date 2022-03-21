package com.canteen.service;

import com.canteen.entity.StMydishes;
import com.canteen.entity.StfoodManagement;
import com.canteen.utils.PageBean;

import java.util.List;
import java.util.Map;

public interface StMyfoodService {

    //获取不同运维餐厅下所有的菜品列表
    PageBean selectByRestaurant(String param);

    //在全量库中根据菜品名称查询菜品信息
    PageBean selectNameByFoodManage(String param);

   //菜品营养分析
    List CalculationNutritional(String dishcode);

    //根据菜品id删除菜品
    void deleteMyFoodById(int id);

    //添加菜品
    void insertMyFood(String param);

    //菜类查询
    List selectdish();

    //------------------------------------菜品查询---------------------------------------------------
    //菜品查询
    Object queryDish(String paramJson);

    //菜品营养成分分析
    Object getNutritional(String paramJson);

    //菜品查询-字典表
    Object queryDishDic();
}
