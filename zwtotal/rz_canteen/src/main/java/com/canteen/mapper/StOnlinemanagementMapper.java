package com.canteen.mapper;

import com.canteen.entity.StOnlinemanagement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StOnlinemanagementMapper {

    //线上菜品管理数量
    Integer getOnlineCnt(@Param("date")String date,@Param("mealType")String mealType,@Param("restaurant")String restaurant);
    //线上菜品管理列表
    List getOnlineList(@Param("date")String date, @Param("mealType")String mealType,@Param("restaurant")String restaurant,
                       @Param("index")Integer index, @Param("pageSize")Integer pageSize);

    //是否支持线上预订
    void updateReserve(StOnlinemanagement stOnlinemanagement);

    //是否支持配送
    void updateDistribution(StOnlinemanagement stOnlinemanagement);
}
