package com.canteen.entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author cdh
 * @since 2020-10-13
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class AppTFlowdetail {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 审批编号，关联主表
     */
    private String flowNo;

    /**
     * 审核人id
     */
    private String auditUserID;

    /**
     * 审核人姓名
     */
    private String auditUserName;

    /**
     * 备注
     */
    private String auditRemark;

    /**
     * 审批时间
     */
    private LocalDateTime auditTime;

    /**
     * 审批状态
     */
    private String auditStatus;

    /**
     * 原因
     */
    private String reason;

    /**
     *流程节点
     */
    private String flowstatus;


}
