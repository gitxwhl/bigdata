package com.canteen.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface StNutritionalAnalysisService {

    //排餐总 能量分析
   Map totalEnergyAnalysis(String param) throws ParseException;

   //排餐能量分布分析
  List distributedEnergyAnalysis(String param);

  //排餐营养素分析
    Map NutrientAnalysis(String param);

    //餐厅经纬度查询
    Map selectLngByRestaurant(String param);

    //菜品膳食结构查询
    Map selectDietaryStructure(String param);

}
