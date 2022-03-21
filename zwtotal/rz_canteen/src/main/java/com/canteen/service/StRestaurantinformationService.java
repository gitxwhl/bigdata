package com.canteen.service;

public interface StRestaurantinformationService {
    //查询餐厅信息
    Object insertStRestaurantinformation(String param);

    //修改早中晚时间
    Object updateMeal(String param);

    //单击修改餐厅信息及餐厅人数
    Object updateMealNumber(String param);
}
