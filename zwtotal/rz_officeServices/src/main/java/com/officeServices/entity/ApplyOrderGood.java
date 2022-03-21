package com.officeServices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyOrderGood {

    private String relateOrderId;
    private String goodId;
    private String goodNo;
    private String goodName;
    private Integer goodAmount;
    private String specs;   //规格
    private String brand;   //品牌
    private String storeNo; //仓库编号
    private String unitPrice;   //库存平均单价
    private String unit;    //单位
    private Integer expectCount; //预警值
    private String pictureUrl;  //图片
    private String location;    //存放地点
}
