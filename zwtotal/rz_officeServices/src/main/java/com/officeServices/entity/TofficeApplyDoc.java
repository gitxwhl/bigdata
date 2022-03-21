package com.officeServices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class TofficeApplyDoc {
    /**
     * 流水号
     */
    private String id;
    /**
     * 办公服务申请单ID
     */
    private String orderID;
    /**
     * 申领单标题
     */
    private String tile;
    /**
     * 会议人数
     */
    private String personAount;
    /**
     * 部门ID
     */
    private String deptId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 申请时间
     */
    private String applyTime;
    /**
     * 申请备注  申请原因
     */
    private String remark;


}
