package com.officeServices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class TstockPlan {
    /**
     *
     */
    private String id;
    /**
     * 申请单编号
     */
    private String applyNo;
    /**
     * 审批状态 0：保存  1：提交  2：已审批 3:已驳回
     */
    private String beginPersonId;
    /**
     *   0：存在  1：不存在
     */
    private String beginPersonName;
    /**
     * 提交人电话
     */
    private String beginPersonPhone;
    /**
     * 订单预计总金额
     */
    private String beginDate;
    /**
     *  总数量
     */
    private String beginDepId;
    /**
     * 驳回原因
     */
    private String beginDepName;
    /**
     * 是否已完成  1：已完成
     */
    private String planTime;
    /**
     * 采购计划名称
     */
    private String endPersonId;
    /**
     * 采购计划名称
     */
    private String endPersonName;
    /**
     * 采购计划名称
     */
    private String endPersonPhone;
    /**
     * 采购计划名称
     */
    private String endDate;
    /**
     * 采购计划名称
     */
    private String operatePersonId;
    /**
     * 采购计划名称
     */
    private String operatePersonName;
    /**
     * 采购计划名称
     */
    private String operateTime;
    /**
     * 采购计划名称
     */
    private String remark;
    /**
     * 采购计划名称
     */
    private String approvelRemark;
    /**
     * 采购计划名称
     */
    private String status;
    /**
     * 采购计划名称
     */
    private String isDel;
    /**
     * 采购计划名称
     */
    private String allMoney;
    /**
     * 采购计划名称
     */
    private String allaMount;
    /**
     * 采购计划名称
     */
    private String backRemark;
    /**
     * 采购计划名称
     */
    private String isComplete;
    /**
     * 采购计划名称
     */
    private String title;






}
