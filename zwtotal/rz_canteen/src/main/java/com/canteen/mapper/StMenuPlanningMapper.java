package com.canteen.mapper;

import com.canteen.entity.StMenuPlanning;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StMenuPlanningMapper{

    //菜单计划查询数量
    Integer getMenusCnt(StMenuPlanning stMenuPlanning);

    //菜单计划查询
    List getMenus(@Param("mealDate")String mealDate, @Param("meals")String meals, @Param("orderBeginTime")String orderBeginTime,
                  @Param("orderEndTime")String orderEndTime, @Param("refundTime")String refundTime, @Param("restaurant")String restaurant,
                  @Param("index")int index,@Param("pageSize")int pageSize);

    //查询时间段里的菜品
    List getDishes(@Param("beginTime")String beginTime,@Param("endTime")String endTime,@Param("mealType")String mealType,
                   @Param("restaurant")String restaurant);

    //编辑订餐及退餐时间
    Integer updateMenuPlan(StMenuPlanning stMenuPlanning);
}
