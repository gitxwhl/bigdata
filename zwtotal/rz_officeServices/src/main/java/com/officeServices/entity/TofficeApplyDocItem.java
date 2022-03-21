package com.officeServices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class TofficeApplyDocItem {

    /**
     *
     */
    private String id;
    /**
     * 办公用品申领单ID
     */
    private String applyDocId;
    /**
     * 流水号
     */
    private String goodId;
    /**
     * 商品编号
     */
    private String goodNo;
    /**
     * 商品名称
     */
    private String goodName;
    /**
     * 数量
     */
    private String goodaMount;
    /**
     * 规格
     */
    private String specs;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 仓库
     */
    private String storeNo;
    /**
     * 单位
     */
    private String unit;
    /**
     * 备注
     */
    private String remark;



}
