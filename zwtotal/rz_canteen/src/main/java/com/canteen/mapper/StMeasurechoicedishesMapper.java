package com.canteen.mapper;

import com.canteen.entity.StMeasurechoicedishes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StMeasurechoicedishesMapper {
    //获取排菜列表
    List choiceDishList(@Param("startIndex")int startIndex,@Param("pageSize")int pageSize,
                        @Param("restaurantName")String restaurantName, @Param("equipmentName")String equipmentName,
                        @Param("dishName")String dishName);

    //获取排菜数量
    int choiceDishCnt(@Param("restaurantName")String restaurantName, @Param("equipmentName")String equipmentName,
                      @Param("dishName")String dishName);

    //删除排菜
    int deleteChoiceDish(@Param("id")String id);

    //查询可关联菜品
    List getFood();

    //新增排菜
    int insertChoiceDish(StMeasurechoicedishes stMeasurechoicedishes);

    //根据菜品号查询菜品数量
    Integer getCntByCode(@Param("dishCode")String dishCode);

    //根据菜品号查询菜品信息
    List getFoodByCode(@Param("dishCode")String dishCode);

    //修改排菜
    int updateChoiceDish(StMeasurechoicedishes stMeasurechoicedishes);

    //导出排菜列表
    List ExportChoiceDishList(@Param("restaurantName")String restaurantName, @Param("equipmentName")String equipmentName,
                              @Param("dishName")String dishName);
}
