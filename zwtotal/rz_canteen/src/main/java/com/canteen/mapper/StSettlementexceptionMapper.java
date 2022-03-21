package com.canteen.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StSettlementexceptionMapper {
    //查询异常列表
    List getExceptionList(@Param("startIndex")int startIndex,@Param("pageSize")int pageSize,
                          @Param("restaurantName")String restaurantName,@Param("startTime")String startTime,
                          @Param("endTime")String endTime,@Param("mealtype")String mealType);

    //查询异常数量
    Integer getExceptionCnt(@Param("restaurantName")String restaurantName,@Param("startTime")String startTime,
                            @Param("endTime")String endTime,@Param("mealtype")String mealType );

    //获取字典表定义
    List dictionaries();
}
