package com.canteen.service;

import com.alibaba.fastjson.JSONArray;
import com.canteen.entity.bo.FoodManagement;
import com.canteen.utils.PageBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;


public interface StfoodManagementService {

    //查询可用食材
    Object getIngredient();

    //根据食材id查询营养成分
    Object getNutritionById(Map<String,Object> map);

    //添加菜品
    String insertFood(FoodManagement foodManagement);

    //查询关联属性（下拉框数据）
    Object getRelevance();

    //菜品查询
    Object getFood(Map<String,Object> map);

    //菜品营养成分分析
    Object foodAnalyze(String paramJson);

    //获取菜品类别
    String getDishCategory(String dishcategory);

    //测试图片上传
    Object imgUploading(HttpServletRequest request, HttpServletResponse response);

    //排序
    String sort(JSONArray array);
}
