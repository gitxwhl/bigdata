package com.atguigu.alibaba.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private Integer id;
    /**
     * 用户id
     */
    private Long userID;
    /**
     *总额度
     */
    private BigDecimal total;
    /**
     *已用余额
     */
    private BigDecimal used;
    /**
     *剩余可用额度
     */
    private BigDecimal  residue;

}
