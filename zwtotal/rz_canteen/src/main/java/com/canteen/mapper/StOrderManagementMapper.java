package com.canteen.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface StOrderManagementMapper {

    //订单数量
    Integer getOrderCnt(@Param("dishName")String dishName,@Param("restaurant")String restaurant,@Param("orderNum")String orderNum,
                        @Param("reservePerson")String reservePerson,@Param("scheduled")String scheduled,
                        @Param("mealType")String mealType,@Param("deduction")String deduction);
    //订单查询
    List getOrder(@Param("dishName")String dishName,@Param("restaurant")String restaurant, @Param("orderNum")String orderNum,
                  @Param("reservePerson")String reservePerson, @Param("scheduled")String scheduled,
                  @Param("mealType")String mealType, @Param("deduction")String deduction,
                  @Param("index")Integer index,@Param("pageSize")Integer pageSize);

    //通过订单号查询订单详情
    Map getOrderByNo(@Param("number")String number);
    //通过订单号查询菜品信息
    List getDishByNo(@Param("orderNo")String orderNo);

}
