package com.canteen.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StFoodtraceabilityMapper {
    //查询菜品溯源列表
    List getList(@Param("index")int index,@Param("pageSize")int pageSize,
                 @Param("dishName")String dishName,@Param("personName")String personName);

    //查询菜品溯源列表数量
    Integer getListCnt(@Param("dishName")String dishName,@Param("personName")String personName);
}
