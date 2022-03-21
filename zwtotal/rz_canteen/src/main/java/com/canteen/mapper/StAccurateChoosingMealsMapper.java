package com.canteen.mapper;

import com.canteen.entity.StAccuratechoosingmeals;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StAccurateChoosingMealsMapper {
    //获取设备列表
    List getNutritionList(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize,
                          @Param("protein") String protein,
                          @Param("fat") String fat,
                          @Param("fattyacids") String fattyacids,
                          @Param("cholesterol") String cholesterol,
                          @Param("carbohydrate") String carbohydrate,
                          @Param("dietaryfiber") String dietaryfiber,
                          @Param("vitamina") String vitamina,
                          @Param("vitamind") String vitamind,
                          @Param("vitamine") String vitamine,
                          @Param("vitamink") String vitamink,
                          @Param("vitaminb1") String vitaminb1,
                          @Param("vitaminb2") String vitaminb2,
                          @Param("vitaminb6") String vitaminb6,
                          @Param("vitamin12") String vitamin12,
                          @Param("vitaminc") String vitaminc,
                          @Param("nicotinicacid") String nicotinicacid,
                          @Param("folicacid") String folicacid,
                          @Param("pantothenicacid") String pantothenicacid,
                          @Param("biotin") String biotin,
                          @Param("choline") String choline,
                          @Param("calcium") String calcium,
                          @Param("phosphorus") String phosphorus,
                          @Param("potassium") String potassium,
                          @Param("sodium") String sodium,
                          @Param("magnesium") String magnesium,
                          @Param("iron") String iron,
                          @Param("zinc") String zinc,
                          @Param("iodine") String iodine,
                          @Param("selenium") String selenium,
                          @Param("copper") String copper,
                          @Param("fluorine") String fluorine,
                          @Param("chromium") String chromium,
                          @Param("manganese") String manganese,
                          @Param("molybdenum") String molybdenum,
                          @Param("restaurant") String restaurant,
                          @Param("platetype") String platetype,
                          @Param("weight") String weight,
                          @Param("energy") String energy,
                          @Param("state") String state);

    Integer getNutritionCnt(
            @Param("protein") String protein,
            @Param("fat") String fat,
            @Param("fattyacids") String fattyacids,
            @Param("cholesterol") String cholesterol,
            @Param("carbohydrate") String carbohydrate,
            @Param("dietaryfiber") String dietaryfiber,
            @Param("vitamina") String vitamina,
            @Param("vitamind") String vitamind,
            @Param("vitamine") String vitamine,
            @Param("vitamink") String vitamink,
            @Param("vitaminb1") String vitaminb1,
            @Param("vitaminb2") String vitaminb2,
            @Param("vitaminb6") String vitaminb6,
            @Param("vitamin12") String vitamin12,
            @Param("vitaminc") String vitaminc,
            @Param("nicotinicacid") String nicotinicacid,
            @Param("folicacid") String folicacid,
            @Param("pantothenicacid") String pantothenicacid,
            @Param("biotin") String biotin,
            @Param("choline") String choline,
            @Param("calcium") String calcium,
            @Param("phosphorus") String phosphorus,
            @Param("potassium") String potassium,
            @Param("sodium") String sodium,
            @Param("magnesium") String magnesium,
            @Param("iron") String iron,
            @Param("zinc") String zinc,
            @Param("iodine") String iodine,
            @Param("selenium") String selenium,
            @Param("copper") String copper,
            @Param("fluorine") String fluorine,
            @Param("chromium") String chromium,
            @Param("manganese") String manganese,
            @Param("molybdenum") String molybdenum,
            @Param("restaurant") String restaurant,
            @Param("platetype") String platetype,
            @Param("weight") String weight,
            @Param("energy") String energy,
            @Param("state") String state);

    //删除营养数据
    Integer deleteNutrition(@Param("id")String id);

    //添加营养
    int  addNutrition(StAccuratechoosingmeals stAccuratechoosingmeals);

    //修改状态
    Integer updateState(@Param("id")Integer id, @Param("state")String state);




}
