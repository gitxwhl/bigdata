package com.officeServices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class TapplyOrderGood {

    /**
     * 流水号
     */
    private String relateorderId;
    /**
     * 流水号
     */
    private String goodId;
    /**
     * 流水号
     */
    private String goodNo;
    /**
     * 流水号
     */
    private String goodName;
    /**
     * 流水号
     */
    private String goodAmount;
    /**
     * 规格
     */
    private String specs;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 仓库编号
     */
    private String storeNo;
    /**
     * 库存平均单价
     */
    private String unitPrice;
    /**
     * 单位
     */
    private String unit;
    /**
     * 预警值
     */
    private String expectCount;
    /**
     * 图片
     */
    private String pictureUrl;

    /**
     * 存放地点
     */
    private String location;



}
