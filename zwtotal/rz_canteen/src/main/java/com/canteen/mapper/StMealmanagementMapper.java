package com.canteen.mapper;

import com.canteen.entity.StMealmanagement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StMealmanagementMapper {

    //直接取餐数量
    Integer directListCnt(@Param("mode")String mode,@Param("name")String name,@Param("restaurant")String restaurant);
    //直接取餐
    List directList(@Param("mode")String mode, @Param("name")String name, @Param("restaurant")String restaurant,
                    @Param("index")Integer index,@Param("pageSize")Integer pageSize);

    //餐柜取餐数量
    Integer plateListCnt(@Param("mode")String mode,@Param("name")String name,
                         @Param("restaurant")String restaurant);
    //餐柜取餐
    List plateList(@Param("mode")String mode, @Param("name")String name,
                   @Param("restaurant")String restaurant,@Param("index")Integer index,@Param("pageSize")Integer pageSize);

    //更改取餐方式
    void updateMode(StMealmanagement stMealmanagement);
}
