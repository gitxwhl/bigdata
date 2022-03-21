package com.canteen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.canteen.entity.StAccuratechoosingmeals;
import com.canteen.mapper.StAccurateChoosingMealsMapper;
import com.canteen.mapper.StOperationrestaurantMapper;
import com.canteen.service.StAccurateChoosingMealsService;
import com.canteen.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StAccurateChoosingMealsServiceImpl implements StAccurateChoosingMealsService {

    @Autowired
    private StAccurateChoosingMealsMapper stAccurateChoosingMealsMapper;
    @Autowired
    private StOperationrestaurantMapper stOperationrestaurantMapper;

    /**
     * 查询餐厅及餐盘种类
     *
     * @return
     */
    @Override
    public Map getDiningRoom() {
        Map<String, List> resultMap = new LinkedHashMap<>();
        //查询餐盘种类 getStOperationList()
//        List Plate =  stAccurateChoosingMealsMapper.getServicePlate();
        List restaurant = stOperationrestaurantMapper.getRestaurant();
        List equipment = stOperationrestaurantMapper.getEquipment();
        resultMap.put("restaurant", restaurant);
        resultMap.put("equipment", equipment);
        return resultMap;
    }

    /*
     * 获取营养列表
     *
     * */
    @Override
    public PageBean getNutritionList(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        JSONObject param = rowData.getJSONObject("param");
        System.out.println("param:" + param);
        String protein = param.getString("protein");
        String fat = param.getString("fat");
        String fattyacids = param.getString("fattyacids");
        String cholesterol = param.getString("cholesterol");
        String carbohydrate = param.getString("carbohydrate");
        String dietaryfiber = param.getString("dietaryfiber");
        String vitamina = param.getString("vitamina");
        String vitamind = param.getString("vitamind");
        String vitamine = param.getString("vitamine");
        String vitamink = param.getString("vitamink");
        String vitaminb1 = param.getString("vitaminb1");
        String vitaminb2 = param.getString("vitaminb2");
        String vitaminb6 = param.getString("vitaminb6");
        String vitamin12 = param.getString("vitamin12");
        String vitaminc = param.getString("vitaminc");
        String nicotinicacid = param.getString("nicotinicacid");
        String folicacid = param.getString("folicacid");
        String pantothenicacid = param.getString("pantothenicacid");
        String biotin = param.getString("biotin");
        String choline = param.getString("choline");
        String calcium = param.getString("calcium");
        String phosphorus = param.getString("phosphorus");
        String potassium = param.getString("potassium");
        String sodium = param.getString("sodium");
        String magnesium = param.getString("magnesium");
        String iron = param.getString("iron");
        String zinc = param.getString("zinc");
        String iodine = param.getString("iodine");
        String selenium = param.getString("selenium");
        String copper = param.getString("copper");
        String fluorine = param.getString("fluorine");
        String chromium = param.getString("chromium");
        String manganese = param.getString("manganese");
        String molybdenum = param.getString("molybdenum");
        //运维餐厅外键 根据传过的code码查询对应的餐厅
        String restaurant = param.getString("restaurant");
        //餐盘种类外键 返现的数据
        String platetype = param.getString("platetype");
        String weight = param.getString("weight");
        String energy = param.getString("energy");
        String state = param.getString("state");
        int totalSize = stAccurateChoosingMealsMapper.getNutritionCnt(
                protein,
                fat,
                fattyacids,
                cholesterol,
                carbohydrate,
                dietaryfiber,
                vitamina,
                vitamind,
                vitamine,
                vitamink,
                vitaminb1,
                vitaminb2,
                vitaminb6,
                vitamin12,
                vitaminc,
                nicotinicacid,
                folicacid,
                pantothenicacid,
                biotin,
                choline,
                calcium,
                phosphorus,
                potassium,
                sodium,
                magnesium,
                iron,
                zinc,
                iodine,
                selenium,
                copper,
                fluorine,
                chromium,
                manganese,
                molybdenum,
                restaurant,
                platetype,
                weight,
                energy,
                state
        );
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if (totalSize == 0) {
            pageBean.setList(new ArrayList());
        } else {
            Integer startIndex = pageBean.getStartIndex();
            List equipmentList = stAccurateChoosingMealsMapper.getNutritionList(startIndex, pageSize,
                    protein,
                    fat,
                    fattyacids,
                    cholesterol,
                    carbohydrate,
                    dietaryfiber,
                    vitamina,
                    vitamind,
                    vitamine,
                    vitamink,
                    vitaminb1,
                    vitaminb2,
                    vitaminb6,
                    vitamin12,
                    vitaminc,
                    nicotinicacid,
                    folicacid,
                    pantothenicacid,
                    biotin,
                    choline,
                    calcium,
                    phosphorus,
                    potassium,
                    sodium,
                    magnesium,
                    iron,
                    zinc,
                    iodine,
                    selenium,
                    copper,
                    fluorine,
                    chromium,
                    manganese,
                    molybdenum,
                    restaurant,
                    platetype,
                    weight,
                    energy,
                    state
            );
            pageBean.setList(equipmentList);
        }
        return pageBean;
    }

    /**
     * 删除营养数据
     * @param param
     * @return
     */
    @Override
    public String deleteNutrition(String param) {
        JSONObject rowData = JSONObject.parseObject(param);
        String id = rowData.getString("id");
        Integer i = stAccurateChoosingMealsMapper.deleteNutrition(id);
        if (i > 0) {
            return "删除成功";
        }
        return "删除失败";
    }

    /**
     * 添加营养
     * @param paramJson
     * @return
     */
    @Override
    public Object addNutrition(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);

        String protein = rowData.getString("protein");
        String fat = rowData.getString("fat");
        String fattyacids = rowData.getString("fattyacids");
        String cholesterol = rowData.getString("cholesterol");
        String carbohydrate = rowData.getString("carbohydrate");
        String dietaryfiber = rowData.getString("dietaryfiber");
        String vitamina = rowData.getString("vitamina");
        String vitamind = rowData.getString("vitamind");
        String vitamine = rowData.getString("vitamine");
        String vitamink = rowData.getString("vitamink");
        String vitaminb1 = rowData.getString("vitaminb1");
        String vitaminb2 = rowData.getString("vitaminb2");
        String vitaminb6 = rowData.getString("vitaminb6");
        String vitamin12 = rowData.getString("vitamin12");
        String vitaminc = rowData.getString("vitaminc");
        String nicotinicacid = rowData.getString("nicotinicacid");
        String folicacid = rowData.getString("folicacid");
        String pantothenicacid = rowData.getString("pantothenicacid");
        String biotin = rowData.getString("biotin");
        String choline = rowData.getString("choline");
        String calcium = rowData.getString("calcium");
        String phosphorus = rowData.getString("phosphorus");
        String potassium = rowData.getString("potassium");
        String sodium = rowData.getString("sodium");
        String magnesium = rowData.getString("magnesium");
        String iron = rowData.getString("iron");
        String zinc = rowData.getString("zinc");
        String iodine = rowData.getString("iodine");
        String selenium = rowData.getString("selenium");
        String copper = rowData.getString("copper");
        String fluorine = rowData.getString("fluorine");
        String chromium = rowData.getString("chromium");
        String manganese = rowData.getString("manganese");
        String molybdenum = rowData.getString("molybdenum");

        String restaurant = rowData.getString("restaurant");

        String platetype = rowData.getString("platetype");
        String weight = rowData.getString("weight");
        String energy = rowData.getString("energy");
        String state = rowData.getString("state");
        StAccuratechoosingmeals stAccuratechoosingmeals = new StAccuratechoosingmeals(0,
                protein,
                fat,
                fattyacids,
                cholesterol,
                carbohydrate,
                dietaryfiber,
                vitamina,
                vitamind,
                vitamine,
                vitamink,
                vitaminb1,
                vitaminb2,
                vitaminb6,
                vitamin12,
                vitaminc,
                nicotinicacid,
                folicacid,
                pantothenicacid,
                biotin,
                choline,
                calcium,
                phosphorus,
                potassium,
                sodium,
                magnesium,
                iron,
                zinc,
                iodine,
                selenium,
                copper,
                fluorine,
                chromium,
                manganese,
                molybdenum,
                restaurant,
                platetype,
                weight,
                energy,
                state
        );
        System.out.println("stAccuratechoosingmeals: " + stAccuratechoosingmeals);
        int i = stAccurateChoosingMealsMapper.addNutrition(stAccuratechoosingmeals);
        if( i > 0 ){
            return "添加成功";
        }
        return "添加失败";
    }
    //自动打菜 修改状态
    @Override
    public String updateState(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        Integer id = Integer.parseInt(rowData.getString("id"));
        String state = rowData.getString("state");
        int i = 0 ;
        if(state.equals("0")){
             i =  stAccurateChoosingMealsMapper.updateState(id,"0");
        }

        if(state.equals("1")){
             i =  stAccurateChoosingMealsMapper.updateState(id,"1");
        }
        if( i > 0 ){
            return "修改状态成功";
        }
        return "修改状态失败";
    }
}
