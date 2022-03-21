package com.canteen.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StMaterialplanMapper {

    //原料需求查询数量
    Integer getMaterialPlanCnt(@Param("beginTime")String beginTime,@Param("endTime")String endTime,
                               @Param("mealType")String mealType,@Param("restaurant")String restaurant);

    //原料需求查询
    List getMaterialPlan(@Param("beginTime")String beginTime, @Param("endTime")String endTime,
                         @Param("mealType")String mealType, @Param("restaurant")String restaurant,
                         @Param("index")int index, @Param("pageSize")int pageSize);

    //导出原料需求查询列表
    List exportMaterialPlan(@Param("beginTime")String beginTime, @Param("endTime")String endTime,
                            @Param("mealType")String mealType, @Param("restaurant")String restaurant);
}
