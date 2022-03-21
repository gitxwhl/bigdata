package com.canteen.mapper;


import com.canteen.entity.StDietaryStructureVo;
import com.canteen.entity.vo.NutrientAnalysisVo;
import com.canteen.entity.vo.NutritionalAnalysisVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface StNutritionalAnalysisMapper {
    //排餐总能量分析-当天
    List<NutritionalAnalysisVo> totalEnergyAnalysis(@Param("restaurant") String restaurant, @Param("date") String date);
    //排餐总能量分析-本周本月
    List<NutritionalAnalysisVo> totalEnergyAnalysisOfWeek(@Param("restaurant") String restaurant, @Param("StartDate") String StartDate,@Param("EndDate") String EndDate);

    //排餐营养素分析
    List<NutrientAnalysisVo> NutrientAnalysis(@Param("restaurant") String restaurant, @Param("date") String date);

    //查询餐厅经纬度
    Map selectLngByRestaurant(@Param("restaurant") String restaurant);

    //菜品膳食结构查询
    List<StDietaryStructureVo> selectDietaryStructure(@Param("restaurant") String restaurant, @Param("date") String date);
}
