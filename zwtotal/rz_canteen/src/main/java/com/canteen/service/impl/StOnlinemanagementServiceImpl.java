package com.canteen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.canteen.entity.StOnlinemanagement;
import com.canteen.mapper.StOnlinemanagementMapper;
import com.canteen.mapper.StfoodManagementMapper;
import com.canteen.service.StOnlinemanagementService;
import com.canteen.service.StfoodManagementService;
import com.canteen.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StOnlinemanagementServiceImpl implements StOnlinemanagementService {

    @Autowired
    private StOnlinemanagementMapper stOnlinemanagementMapper;

    @Autowired
    private StOperationrestaurantServiceImpl stOperationrestaurantService;

    @Autowired
    private StfoodManagementMapper stfoodManagementMapper;

    @Autowired
    private StfoodManagementService stfoodManagementService;

    /*
    * 线上菜品管理列表
    * {"pageNum":"","pageSize":"","date":"","mealType":"","restaurant":""}
    * */
    @Override
    public Object getOnlineList(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        String date = rowData.getString("date");
        String mealType = rowData.getString("mealType");
        String str = rowData.getString("restaurant");
        String restaurant = stOperationrestaurantService.getIds(str);
        Integer totalSize = stOnlinemanagementMapper.getOnlineCnt(date, mealType,restaurant);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if(totalSize == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List onlineList = stOnlinemanagementMapper.getOnlineList(date, mealType, restaurant, startIndex, pageSize);
            pageBean.setList(onlineList);
        }
        return pageBean;
    }

    /*
    * 是否支持线上预订
    * {"id":"","isReserve":""}
    * */
    @Override
    public Object updateReserve(StOnlinemanagement stOnlinemanagement) {
        String isReserve = stOnlinemanagement.getIsReserve();
        if(isReserve.equals("是")){
            stOnlinemanagement.setIsReserve("否");
        }
        if(isReserve.equals("否")){
            stOnlinemanagement.setIsReserve("是");
        }
        stOnlinemanagementMapper.updateReserve(stOnlinemanagement);
        return "修改成功";
    }

    /*
     * 是否支持配送
     * {"id":"","isDistribution":""}
     * */
    @Override
    public Object updateDistribution(StOnlinemanagement stOnlinemanagement) {
        String isDistribution = stOnlinemanagement.getIsDistribution();
        if(isDistribution.equals("是")){
            stOnlinemanagement.setIsDistribution("否");
        }
        if(isDistribution.equals("否")){
            stOnlinemanagement.setIsDistribution("是");
        }
        stOnlinemanagementMapper.updateDistribution(stOnlinemanagement);
        return "修改成功";
    }

    //获取下拉框数据
    @Override
    public Object dropDownBox() {
        Map resultMap = new LinkedHashMap<>();

        //餐别
        List mealType = new ArrayList();
        mealType.add(JSONObject.parseObject("{'id':'','text':'全部'}"));

        List<Map> dictionaries = stfoodManagementMapper.dictionaries();
        dictionaries.stream().forEach(map -> {
            JSONObject jsonObject = JSONObject.parseObject("{'id':'"+map.get("parakey")+"','text':'"+map.get("paravalue")+"'}");
            if(map.get("paraname").equals("mealtype")){
                mealType.add(jsonObject);
            }
        });

        resultMap.put("mealType",mealType);
        return resultMap;
    }

    /*
     * 菜品营养成分分析
     * {"dishcode":""}
     * */
    @Override
    public Object getNutritional(String param) {
        Object o = stfoodManagementService.foodAnalyze(param);
        return o;
    }


}
