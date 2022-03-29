package com.atguigu.springcloud.mapper;

import com.atguigu.springcloud.entitys.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaymentMapper
{
     int create(Payment payment);

     Payment getPaymentById(@Param("id") Long id);
}
