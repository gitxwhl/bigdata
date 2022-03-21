package com.canteen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.canteen.entity.StMenuPlanning;
import com.canteen.mapper.StMenuPlanningMapper;
import com.canteen.mapper.StOperationrestaurantMapper;
import com.canteen.service.StMenuPlanningService;
import com.canteen.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StMenuPlanningServiceImpl implements StMenuPlanningService {

    @Autowired
    private StMenuPlanningMapper stMenuPlanningMapper;

    @Autowired
    private StOperationrestaurantServiceImpl stOper;

    @Autowired
    private StOperationrestaurantMapper st;

    /*
    * 菜单计划查询
    * {"pageNum":"","pageSize":"","meals":"","restaurant":"","orderBeginTime":"","orderEndTime":"","refundTime":""
    * ,"mealDate":""}
    * */
    @Override
    public Object getMenus(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        String meals = rowData.getString("meals");
        String str = rowData.getString("restaurant");
        String restaurant = stOper.getIds(str);
        String orderBeginTime = rowData.getString("orderBeginTime");
        String orderEndTime = rowData.getString("orderEndTime");
        String refundTime = rowData.getString("refundTime");
        String mealDate = rowData.getString("mealDate");
        StMenuPlanning stMenuPlanning = new StMenuPlanning(0,meals,restaurant,null,orderBeginTime,orderEndTime,refundTime,mealDate,"0");
        Integer totalSize = stMenuPlanningMapper.getMenusCnt(stMenuPlanning);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if( totalSize == 0 ){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List<StMenuPlanning> menus = stMenuPlanningMapper.getMenus(mealDate,meals,orderBeginTime,orderEndTime,
                    refundTime,restaurant, startIndex, pageSize);
            for (StMenuPlanning menu : menus) {
                String mealType = menu.getMeals(); //餐别
                String beginTime = menu.getOrderBeginTime(); //订餐开始时间
                String endTime = menu.getOrderEndTime(); //订餐结束时间
                String restaurant1 = menu.getRestaurant(); //餐厅
                List<Map> dishes = stMenuPlanningMapper.getDishes(beginTime, endTime, mealType,restaurant1);
                if( dishes != null && dishes.size() != 0 ){
                    StringBuilder sb = new StringBuilder();
                    for (Map dish : dishes) {
                        sb.append(dish.get("name")).append("、");
                    }
                    String substring = sb.substring(0, sb.length() - 1);
                    menu.setMenu(substring);
                }
            }
            pageBean.setList(menus);
        }
        return pageBean;
    }

    @Override
    public Object getRelevance() {
        //餐别
        List<JSONObject> mealType = new ArrayList<>();
        mealType.add(JSONObject.parseObject("{'id':'','text':'全部'}"));

        List<Map> dictionaries = st.dictionaries();

        dictionaries.stream().forEach(map -> {
            JSONObject jsonObject = JSONObject.parseObject("{'id':'"+map.get("parakey")+"','text':'"+map.get("paravalue")+"'}");
            if("mealtype".equals(map.get("paraname"))){
                mealType.add(jsonObject);
            }
        });
        return mealType;
    }

    /*
    * 编辑订餐及退餐时间
    * {"id":"","orderBeginTime":"","orderEndTime":"","refundTime":""}
    * */
    @Override
    public Object updateMenuPlan(StMenuPlanning stMenuPlanning) {
        stMenuPlanningMapper.updateMenuPlan(stMenuPlanning);
        return "修改成功";
    }
}
