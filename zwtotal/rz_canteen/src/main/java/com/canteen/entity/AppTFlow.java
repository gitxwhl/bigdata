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
public class AppTFlow {

    private static final long serialVersionUID=1L;

    /**
     * 审批编号
     */
    private String FlowNo;

    /**
     * 标题
     */
    private String Title;

    /**
     * 审批类型
     */
    private String BusType;

    /**
     * 申请人
     */
    private String AddUserNo;

    /**
     * 添加时间
     */
    private LocalDateTime AddTime;

    /**
     * 审核状态(1.待审,2.通过.3.驳回,4.撤销)
     */
    private Integer ApproStatus;


}
