package com.atguigu.alibaba.domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
/**
 * @auther zzyy
 * @create 2020-02-26 15:16
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order
{
    private Long id;

    private Long userId;

    private Long productId;
    //数量
    private Integer count;
    //金额
    private BigDecimal money;
    //订单状态:  0:创建中 1:已完结
    private Integer status; //订单状态：0：创建中；1：已完结
}
