package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@Data
@AllArgsConstructor
public class StSettlementexception {
    private Integer id;

    //餐厅
    private String restaurant;

    //卡号
    private String cardNumber;

    //菜品
    private String varietyDishes;

    //金额
    private Double amountMoney;

    //异常原因
    private String abnormalCause;

    //取餐时间
    private String mealTime;

    //餐别
    private String mealType;
}
