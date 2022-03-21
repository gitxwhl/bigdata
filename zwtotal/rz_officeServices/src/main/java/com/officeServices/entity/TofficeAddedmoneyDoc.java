package com.officeServices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class TofficeAddedmoneyDoc {
    /**
     * 流水号
     */
    private String id;
    /**
     * 办公服务申请单ID
     */
    private String orderid;
    /**
     * 年度
     */
    private String theyear;
    /**
     * 部门ID
     */
    private String deptID;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 办公费已执行数
     */
    private String  amout;
    /**
     * 额外申请金额
     */
    private String addedMoney;
    /**
     * 申请时间
     */
    private String crateTime;
    /**
     * 备注
     */
    private String remark;





}
