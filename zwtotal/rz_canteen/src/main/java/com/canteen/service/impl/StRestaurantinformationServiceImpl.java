package com.canteen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.canteen.entity.StOperationrestaurant;
import com.canteen.entity.StRestaurantinformation;
import com.canteen.mapper.StOperationrestaurantMapper;
import com.canteen.mapper.StRestaurantinformationMapper;
import com.canteen.service.StRestaurantinformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StRestaurantinformationServiceImpl implements StRestaurantinformationService {

    @Autowired
    private StRestaurantinformationMapper stRestaurantinformationMapper;

    @Autowired
    private StOperationrestaurantServiceImpl stOperationrestaurantService;

    @Autowired
    private StOperationrestaurantMapper stOperationrestaurantMapper;

    /**
     * 查询餐厅信息
     *
     * @return
     */
    @Override
    public Object insertStRestaurantinformation(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        String str = rowData.getString("restaurant");
        Integer id = Integer.valueOf(str);
        StOperationrestaurant Introduce = stOperationrestaurantMapper.selectIntroduce(id);
        System.out.println("Introduce:" + Introduce);
        String restaurant = stOperationrestaurantService.getIds(str);
        StRestaurantinformation morning = stRestaurantinformationMapper.insertMorning(restaurant);
        StRestaurantinformation ChineseFast = stRestaurantinformationMapper.insertChinesefast(restaurant);
        StRestaurantinformation supper = stRestaurantinformationMapper.insertSupper(restaurant);
        Map map = new HashMap();
        map.put("Introduce", Introduce);
        map.put("morning", morning);
        map.put("centre", ChineseFast);
        map.put("supper", supper);
        return map;
    }

    /**
     * 修改时间
     *
     * @return
     * @paramJson paramJson
     */
    @Override
    public Object updateMeal(String paramJson) {
        int i = 0;
        JSONObject rowData = JSONObject.parseObject(paramJson);

        String supplystarttime = rowData.getString("supplystarttime");
        String supplyendtime = rowData.getString("supplyendtime");
        String givestarttime = rowData.getString("givestarttime");
        String giveendtime = rowData.getString("giveendtime");
        String upperstarttime = rowData.getString("upperstarttime");
        String upperendtime = rowData.getString("upperendtime");
        String mealintroduction = rowData.getString("mealintroduction");
        String dictionary = rowData.getString("dictionary");
        String dishes = rowData.getString("dishes");
        String restaurant = rowData.getString("restaurant");
        if (rowData.getString("id").equals("")) {
            i = stRestaurantinformationMapper.insertMeal(0,
                    supplystarttime,
                    supplyendtime,
                    givestarttime,
                    giveendtime,
                    upperstarttime,
                    upperendtime,
                    mealintroduction,
                    dictionary, dishes, restaurant);
        }
        if (rowData.getString("id") != null && !rowData.getString("id").equals("")) {
            Integer id = Integer.valueOf(rowData.getString("id"));
            i = stRestaurantinformationMapper.updateMeal(id,
                    supplystarttime,
                    supplyendtime,
                    givestarttime,
                    giveendtime,
                    upperstarttime,
                    upperendtime,
                    mealintroduction,
                    dictionary, dishes, restaurant);

        }

        if (i > 0) {
            return "保存成功";
        }
        return "保存失败";
    }

    /**
     * 单击修改餐厅信息及餐厅人数
     *
     * @param paramJson
     * @return
     */
    @Override
    public Object updateMealNumber(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        Integer id = Integer.valueOf(rowData.getString("id"));
        String capacity = rowData.getString("capacity");
        String introduction = rowData.getString("introduction");
        int i = stOperationrestaurantMapper.updateMealNumber(id, capacity, introduction);
        if (i > 0) {
            return "修改成功";
        }
        return "修改失败";
    }
}
