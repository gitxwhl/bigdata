package com.officeServices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class TstockPlanRecord {

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
     *实际单价
     */
    private String realUnitprice;
    /**
     *总价格
     */
    private String realAmount;
    /**
     *
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
     *操作人
     */
    private String operatePersonId;
    /**
     *操作人
     */
    private String operatePersonName;
    /**
     *操作时间
     */
    private String operateTime;
    /**
     *操作类型  0:入库  1：已上架
     */
    private String isUp;
    /**
     *预警状态
     */
    private String isWarn;
    /**
     *到货单号
     */
    private String arrivalOrderId;
    /**
     *存放地点
     */
    private String location;
    /**
     *t_stock_plan_record_main表主键
     */
    private String mainId;


}
