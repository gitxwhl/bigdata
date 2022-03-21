package com.officeServices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.ByteArrayInputStream;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Tstock {

    /**
     * 办公用品申领单ID
     */
    private String id;
    /**
     * 物品编号
     */
    private String goodNo;
    /**
     * 物品编号
     */
    private String goodName;
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
     * 库存金额
     */
    private String allmoney;
    /**
     * 可用库存
     */
    private String usableaMount;
    /**
     * 待入库
     */
    private String inStore;
    /**
     * 待出库
     */
    private String outStore;
    /**
     * 当前库存
     */
    private String nowStore;
    /**
     * 商品类型父节点
     */
    private String parenttypeId;
    /**
     * 单位
     */
    private String unit;
    /**
     * 图片流
     */
    private byte[] picture;
    /**
     * 图片地址
     */
    private String pictureurl;
    /**
     * 是否存在  0：存在物资  1：已移除
     */
    private String isDel;
    /**
     * 预警值
     */
    private String expectcount;
    /**
     * 条形码
     */
    private String barCode;
    /**
     * 创建时间
     */
    private String creatTime;

}
