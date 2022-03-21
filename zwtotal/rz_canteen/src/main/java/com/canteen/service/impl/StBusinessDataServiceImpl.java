package com.canteen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.canteen.entity.StOperationrestaurant;
import com.canteen.mapper.StBusinessDataMapper;
import com.canteen.service.StBusinessDataService;
import com.canteen.service.StfoodManagementService;
import com.canteen.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StBusinessDataServiceImpl implements StBusinessDataService {

    @Autowired
    StBusinessDataMapper stBusinessDataMapper;

    @Autowired
    private StOperationrestaurantServiceImpl serviceImpl;

    @Autowired
    private StfoodManagementService stfoodManagementService;

    /*
    查询营业数据
     */
    @Override
    public PageBean selectStBusinessData(String param) {
        JSONObject paramData = JSONObject.parseObject(param);
        String restaurant = paramData.getString("restaurant");
        String startData = paramData.getString("startData");
        String endData = paramData.getString("endData");
        int pageNum = Integer.parseInt(paramData.getString("pageNum"));
        int pageSize = Integer.parseInt(paramData.getString("pageSize"));
        //每次查询营业数据前先清空表数据
        stBusinessDataMapper.deleteDate();
        //调用添加数据方法
        insertStBusinessData(restaurant, startData, endData);
        Integer totalSize = stBusinessDataMapper.selectDataCount();
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        Map map = new HashMap();
        if (totalSize == 0) {
            pageBean.setList(new ArrayList());
        } else {
            Integer startIndex = pageBean.getStartIndex();
            List<Map> mapList = stBusinessDataMapper.selectStBusinessData(startIndex, pageSize);
            for (Map map1 : mapList) {
                String category = map1.get("category").toString();
                String dishCategory = stfoodManagementService.getDishCategory(category);
                map1.put("category",dishCategory);
            }
            Map map1 = stBusinessDataMapper.selectTotal();
            Integer total = Integer.parseInt(String.valueOf(map1.get("salestotal")));
            Integer face = Integer.parseInt(String.valueOf(map1.get("face")));
            Integer card = Integer.parseInt(String.valueOf(map1.get("card")));
            Integer guest = Integer.parseInt(String.valueOf(map1.get("guest")));
            map.put("datalist", mapList);
            map.put("total", total);
            map.put("face", face);
            map.put("card", card);
            map.put("guest", guest);
            pageBean.setMap(map);
        }
        return pageBean;
    }

    /*
    往st_businessdata表里添加某段时间的营业数据
     */
    public void insertStBusinessData(String restaurant, String startData, String endData) {
        String ids = serviceImpl.getIds(restaurant);
        //查询订单编号和这笔订单的取餐方式
        List<Map> mapList = (List<Map>) stBusinessDataMapper.selectOrderAndWay(ids, startData, endData);
        for (Map map : mapList) {
            String ordernumber = (String) map.get("ordernumber");
            String pickway = (String) map.get("pickway");
            String scheduled = (String) map.get("scheduled");
            //拿到订单编号去查询对应的菜品id
            List<Integer> list = stBusinessDataMapper.selectIdByOrderNumber(ordernumber);
            for (Integer id : list) {
                //拿到对应id的菜品详细信息
                Map map1 = stBusinessDataMapper.selectDishById(id);
                String meals = (String) map1.get("meals");
                String name = (String) map1.get("name");
                String dishescategory = (String) map1.get("dishescategory");
                String taste = (String) map1.get("taste");
                String category = (String) map1.get("category");

                //向营业数据表中查询对应的菜品数据
                if (pickway.equals("1")) {
                    //判断此id是否存在表中
                    if (stBusinessDataMapper.selectNameById(id) == null) {
                        stBusinessDataMapper.insertData(meals, name, dishescategory, taste, category, id, scheduled);
                        stBusinessDataMapper.updateFace(id);
                    } else {
                        stBusinessDataMapper.updateFace(id);
                    }
                } else if (pickway.equals("2")) {
                    if (stBusinessDataMapper.selectNameById(id) == null) {
                        stBusinessDataMapper.insertData(meals, name, dishescategory, taste, category, id, scheduled);
                        stBusinessDataMapper.updateCard(id);
                    } else {
                        stBusinessDataMapper.updateCard(id);
                    }

                } else if (pickway.equals("3")) {
                    if (stBusinessDataMapper.selectNameById(id) == null) {
                        stBusinessDataMapper.insertData(meals, name, dishescategory, taste, category, id, scheduled);
                        stBusinessDataMapper.updateGuest(id);
                    } else {
                        stBusinessDataMapper.updateGuest(id);
                    }
                }
            }
        }
    }

    public List getIds(String string) {
        List list = new ArrayList();
        String restaurantName = null;
        StringBuilder sb = new StringBuilder();
        if (string != null && !string.equals("")) {
            //Integer restaurantId = Integer.parseInt(string);
            //通过id查询parend_ids，decide
            List<Map> decide = stBusinessDataMapper.getDecide(string);
            int parentIds = (Integer) decide.get(0).get("parentIds");
            //判断是否为一级菜单
            if (parentIds == 0) {
                List<String> list1 = stBusinessDataMapper.selectRestaurantCode();
                for (String li : list1) {
                    list.add(li);
                }
            } else {
                int decide2 = (Integer) decide.get(0).get("decide");
                //判断是否为二级菜单
                if (decide2 == 2) { //二级菜单
                    List<StOperationrestaurant> opById = stBusinessDataMapper.getOpById(decide2);
                    if (opById != null && opById.size() != 0) {
                        for (StOperationrestaurant stOperationrestaurant : opById) {
                            //sb.append(stOperationrestaurant.getRestaurantCode()).append(",");
                            list.add(stOperationrestaurant.getRestaurantCode());
                        }
                    }
                } else { //三级菜单
                    restaurantName = string;
                    list.add(restaurantName);
                }
            }
        }
        return list;
    }
}
