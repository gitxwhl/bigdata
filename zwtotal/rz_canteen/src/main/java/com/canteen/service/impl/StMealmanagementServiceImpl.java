package com.canteen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.canteen.entity.StMealmanagement;
import com.canteen.mapper.StMealmanagementMapper;
import com.canteen.mapper.StOperationrestaurantMapper;
import com.canteen.service.StMealmanagementService;
import com.canteen.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StMealmanagementServiceImpl implements StMealmanagementService {

    @Autowired
    private StMealmanagementMapper stMealmanagementMapper;

    @Autowired
    private StOperationrestaurantServiceImpl serviceImpl;

    @Autowired
    private StOperationrestaurantMapper operationrestaurantMapper;


    /*
    * 直接取餐列表
    * {"pageNum":"","pageSize":"","mode":"","name":"","restaurant":""}
    * */
    @Override
    public Object directList(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        String mode = rowData.getString("mode");
        String name = rowData.getString("name");
        String str = rowData.getString("restaurant");
        String restaurant = serviceImpl.getIds(str);
        Integer directListCnt = stMealmanagementMapper.directListCnt(mode, name, restaurant);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(directListCnt);
        if (directListCnt == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List list = stMealmanagementMapper.directList(mode, name, restaurant, startIndex, pageSize);
            pageBean.setList(list);
        }
        return pageBean;
    }

    /*
    * 餐柜取餐列表
    * {"pageNum":"","pageSize":"","mode":"","name":"","restaurant":""}
    * */
    @Override
    public Object plateList(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        String mode = rowData.getString("mode");
        String name = rowData.getString("name");
        String str = rowData.getString("restaurant");
        String restaurant = serviceImpl.getIds(str);
        Integer plateListCnt = stMealmanagementMapper.plateListCnt(mode, name, restaurant);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(plateListCnt);
        if (plateListCnt == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List list = stMealmanagementMapper.plateList(mode, name, restaurant, startIndex, pageSize);
            pageBean.setList(list);
        }
        return pageBean;
    }

    /*
    * 更改取餐方式
    * {"id":"","card":"","face":"","number":""}
    * */
    @Override
    public Object updateMode(StMealmanagement stMealmanagement) {
        String card = stMealmanagement.getCard();
        String face = stMealmanagement.getFace();
        String number = stMealmanagement.getNumber();
        if(card != null && !card.equals("")){
            if(card.equals("是")){
                stMealmanagement.setCard("否");
            }else if(card.equals("否")){
                stMealmanagement.setCard("是");
            }
        }
        if(face != null && !face.equals("")){
            if(face.equals("是")){
                stMealmanagement.setFace("否");
            }else if(face.equals("否")){
                stMealmanagement.setFace("是");
            }
        }
        if(number != null && !number.equals("")) {
            if (number.equals("是")) {
                stMealmanagement.setNumber("否");
            } else if (number.equals("否")) {
                stMealmanagement.setNumber("是");
            }
        }
        stMealmanagementMapper.updateMode(stMealmanagement);
        return "更改成功";
    }

    /*
    * 获取下拉框数据
    * */
    @Override
    public Object dropDownBox() {
        Map resultMap = new LinkedHashMap<>();

        //取餐方式
        List mode = new ArrayList();
        mode.add(JSONObject.parseObject("{'id':'','text':'全部'}"));

        List<Map> dictionaries = operationrestaurantMapper.dictionaries();
        dictionaries.stream().forEach(map ->{
            JSONObject jsonObject = JSONObject.parseObject("{'id':'" + map.get("parakey") + "','text':'" + map.get("paravalue") + "'}");
            if("mode".equals(map.get("paraname"))){
                mode.add(jsonObject);
            }
        });

        resultMap.put("mode",mode);
        return resultMap;
    }
}
