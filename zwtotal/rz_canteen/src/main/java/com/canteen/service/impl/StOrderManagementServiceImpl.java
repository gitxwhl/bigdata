package com.canteen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.canteen.mapper.StFoodtraceabilityMapper;
import com.canteen.mapper.StOrderManagementMapper;
import com.canteen.mapper.StfoodManagementMapper;
import com.canteen.service.StOrderManagementService;
import com.canteen.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StOrderManagementServiceImpl implements StOrderManagementService {

    @Autowired
    private StOrderManagementMapper stOrderManagementMapper;
    
    @Autowired
    private StOperationrestaurantServiceImpl stOperationrestaurantService;

    @Autowired
    private StfoodManagementMapper stfoodManagementMapper;

    /*
    *订单列表
    * {"pageNum":"","pageSize":"","restaurant":"","orderDish":"","orderNum":"","reservePerson":"","scheduled":"","mealType":"","deduction":""}
    * */
    @Override
    public Object getOrder(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        String str = rowData.getString("restaurant");
        String restaurant = stOperationrestaurantService.getIds(str);
        String orderDish = rowData.getString("orderDish");
        String orderNum = rowData.getString("orderNum");
        String reservePerson = rowData.getString("reservePerson");
        String scheduled = rowData.getString("scheduled");
        String mealType = rowData.getString("mealType");
        String deduction = rowData.getString("deduction");
        Integer totalSize = stOrderManagementMapper.getOrderCnt(orderDish,restaurant, orderNum, reservePerson, scheduled, mealType, deduction);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if(totalSize == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List<Map> order = stOrderManagementMapper.getOrder(orderDish,restaurant, orderNum, reservePerson, scheduled, mealType, deduction, startIndex, pageSize);
            pageBean.setList(order);
        }
        return pageBean;
    }

    /*
    * 通过订单号查询订单详情
    * {"orderNumber":""}
    * */
    @Override
    public Object getOrderByNo(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        String orderNumber = rowData.getString("orderNumber");
        Map orderByNo = stOrderManagementMapper.getOrderByNo(orderNumber);
        List dishByNo = stOrderManagementMapper.getDishByNo(orderNumber);
        orderByNo.put("dishs",dishByNo);
        return orderByNo;
    }

    /*
    * 获取下拉框数据
    * */
    @Override
    public Object dropDownBox() {
        Map resultMap = new LinkedHashMap();

        //餐别
        List mealType = new ArrayList();
        mealType.add(JSONObject.parseObject("{'id':'','text':'全部'}"));

        //扣费情况
        List deduction = new ArrayList();
        deduction.add(JSONObject.parseObject("{'id':'','text':'全部'}"));

        List<Map> dictionaries = stfoodManagementMapper.dictionaries();
        dictionaries.stream().forEach(map -> {
            JSONObject jsonObject = JSONObject.parseObject("{'id':'"+map.get("parakey")+"','text':'"+map.get("paravalue")+"'}");
            if("mealtype".equals(map.get("paraname"))){
                mealType.add(jsonObject);
            }
            if("deduction".equals(map.get("paraname"))){
                deduction.add(jsonObject);
            }
        });
        resultMap.put("mealType",mealType);
        resultMap.put("deduction",deduction);
        return resultMap;
    }
}
