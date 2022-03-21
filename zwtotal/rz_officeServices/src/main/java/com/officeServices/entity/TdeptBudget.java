package com.officeServices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class TdeptBudget {

    /**
     * 流水号
     */
    private String id;
    /**
     * 流水号
     */
    private String orgid;
    /**
     * 流水号
     */
    private String orgname;
    /**
     * 部门预算
     */
    private String maxmoney;
    /**
     * 额外预算
     */
    private String addedMoney;
    /**
     * 年度
     */
    private String theyear;
    /**
     * 年度开始时间
     */
    private String beginDate;
    /**
     * 年度结束时间
     */
    private String endDate;
    /**
     * 备注
     */
    private String remark;
    /**
     * 0：办公用品   1：打印纸
     */
    private String isPaper;
    /**
     * 操作时间
     */
    private String operateTime;





}
