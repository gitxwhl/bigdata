package com.atguigu.alibaba.service;

import com.atguigu.alibaba.domain.Order;
import org.apache.ibatis.annotations.Param;

public interface OrderService {

    /**
     * 创建订单
     */
    void create(@Param("order") Order order);

}
