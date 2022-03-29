package com.atguigu.alibaba.dao;

import com.atguigu.alibaba.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDao {
        /**
        *下订单
         */
        int create(@Param("order") Order order);


        /**
        *修改订单状态
         */
        int upedate(@Param("userId")Long userId,@Param("status")Integer status);







}
