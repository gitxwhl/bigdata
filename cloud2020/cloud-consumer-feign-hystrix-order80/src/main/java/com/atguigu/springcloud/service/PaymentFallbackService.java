package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * @auther 统一处理此接口涉及的方法，出现服务提供者出现宕机或者其他的现象
 * @create 2020-02-20 18:22
 */
@Component
public class PaymentFallbackService implements PaymentHystrixService
{
    @Override
    public String paymentInfo_OK(Integer id)
    {
        return "-----PaymentFallbackService 宕机 fall back-paymentInfo_OK ,o(╥﹏╥)o";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id)
    {
        return "-----PaymentFallbackService 宕机 fall back-paymentInfo_TimeOut ,o(╥﹏╥)o";
    }
}
