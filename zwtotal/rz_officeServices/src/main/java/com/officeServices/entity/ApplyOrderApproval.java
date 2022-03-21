package com.officeServices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyOrderApproval {
    /**
     * 流水号
     */
    private String id;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 单据状态：0：提交  1：审批   2:驳回
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 驳回备注
     */
    private String backRemark;

    /**
     * 角色
     */
    private String roleType;

    /**
     * 处理时间
     */
    private String createTime;

    /**
     * 创建时间
     */
    private String dealTime;

    /**
     * 审批人id
     */
    private String dealUserId;

    /**
     * 审批人
     */
    private String dealUserName;

    /**
     * 处理人部门ID
     */
    private String dealUserDeptId;

    /**
     * 处理人部门name
     */
    private String dealUserDeptName;

    /**
     *
     */
    private String step;


}
