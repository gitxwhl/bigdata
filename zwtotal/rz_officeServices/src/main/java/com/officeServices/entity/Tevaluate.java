package com.officeServices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tevaluate {
    //id
    private Integer id;

    //评价内容
    private String content;

    //评价星级
    private String number;

    //图片地址
    private String pictureUrl;

    //物品编号
    private String goodNo;

    //申请单号
    private String orderId;

}
