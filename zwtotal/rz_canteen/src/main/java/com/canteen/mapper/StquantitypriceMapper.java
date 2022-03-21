package com.canteen.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StquantitypriceMapper {

    //获取列表数量
    int getCommodityCnt(@Param("dishname")String dishname ,@Param("restaurant")String restaurant);

    //获取列表数据
    List getCommodityList(@Param("startIndex") int startIndex,
                          @Param("pageSize")  int pageSize,
                          @Param("dishname")  String dishname,
                          @Param("restaurant")String restaurant);

    int amendPrice(@Param("id") Integer id,
                   @Param("timepice") String timepice,
                   @Param("Outtimepice") String Outtimepice,
                   @Param("employeecardprice") String employeecardprice,
                   @Param("temporarycardPrice") String temporarycardPrice);
}
