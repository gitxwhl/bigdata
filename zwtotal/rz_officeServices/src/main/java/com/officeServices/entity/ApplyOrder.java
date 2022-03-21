package com.officeServices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ApplyOrder {
    /**
     * 流水号
     */
    private String id;
    /**
     * 订单号
     */
    private String orderId;

    /**
     * 提交人id
     */
    private String beginPersonId;
    /**
     * 提交人
     */
    private String beginPersonName;
    /**
     * 提交日期
     */
    private String beginDate;
    /**
     * 流水号
     */
    private String attr1;
    /**
     * 预留字段
     */
    private String attr2;
    /**
     * 预留字段
     */
    private String attr3;
    /**
     * 图片地址
     */
    private String pictures;
    /**
     * 单据状态：0：提交  1：审批   2:驳回
     */
    private String status;
    /**
     * 需求部门
     */
    private String demandDep;
    /**
     * 需求部门id
     */
    private String demandDepId;
    /**
     * 交货日期
     */
    private String receiptDate;
    /**
     * 备注
     */
    private String remark;

    /**
     *仓库
     */
    private String store;
    /**
     * 用途
     */
    private String useInfo;
    /**
     * 订单总金额
     */
    private BigDecimal amountPrice;
    /**
     * 驳回原因
     */
    private String bhyy;
    /**
     * 类型
     */
    private String typeId;
    /**
     * 类型
     */
    private String isOut;
    /**
     * 会议名称
     */
    private String meetingName;
    /**
     * 会议人数
     */
    private String meetingPersonNumber;
    /**
     * 0:办公用品  1：打印纸
     */
    private String isPaper;
}
