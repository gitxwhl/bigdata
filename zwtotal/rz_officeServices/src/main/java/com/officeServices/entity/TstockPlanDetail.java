package com.officeServices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class TstockPlanDetail {
    /**
     *
     */
    private String id;
    /**
     *主申请单编号
     */
    private String applyNo;
    /**
     *商品编号
     */
    private String goodId;
    /**
     *物品编号
     */
    private String goodNo;
    /**
     *物品名称
     */
    private String goodName;
    /**
     *规格
     */
    private String specs;
    /**
     *品牌
     */
    private String brand;
    /**
     *仓库编号
     */
    private String storeNo;
    /**
     *库存平均单价
     */
    private String unitPrice;
    /**
     *计划数量
     */
    private String amount;
    /**
     *库存金额
     */
    private String allmoney;
    /**
     *实际入库单价
     */
    private String realUnitprice;
    /**
     *实际入库总数量
     */
    private String realAmount;
    /**
     *实际入库总金额
     */
    private String realAllmoney;
    /**
     *商品类型父节点
     */
    private String parenttypeId;
    /**
     *单位
     */
    private String unit;
    /**
     *图片地址
     */
    private String pictureUrl;
    /**
     *计划审批时间
     */
    private String planTime;

    /**
     *  0：存在  1：不存在
     */
    private String isDel;
    /**
     *预警状态  1:预警  0：未预警
     */
    private String isWarn;
    /**
     *实际上架单价
     */
    private String upRealUnitprice;
    /**
     *实际上架总数
     */
    private String upRealAmount;
    /**
     *实际上架金额
     */
    private String upRealAllmoney;
    /**
     *预警值
     */
    private String expectCount;
    /**
     *存放地点
     */
    private String location;







}
