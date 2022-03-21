package com.canteen.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="异地就餐参数",description="异地就餐对象")
public class AppTApplyCanteen {

    private static final long serialVersionUID=1L;


    private String id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private String userId;

    /**
     * 用户姓名
     */
    @ApiModelProperty(value = "用户姓名")
    private String userName;

    /**
     * 用户联系方式
     */
    @ApiModelProperty(value = "用户联系方式")
    private String userPhone;

    /**
     * 用户所在组织ID
     */
    @ApiModelProperty(value = "用户所在组织ID")
    private String orgId;

    /**
     * 用户所在组织名称
     */
    @ApiModelProperty(value = "用户所在组织名称")
    private String orgName;

    /**
     * 用户部门ID
     */
    @ApiModelProperty(value = "用户部门ID")
    private String deptId;

    /**
     * 用户部门名称
     */
    @ApiModelProperty(value = "用户部门名称")
    private String deptName;

    /**
     * 用户申请组织ID
     */
    @ApiModelProperty(value = "用户申请组织ID")
    private String applyOrgId;


    @ApiModelProperty(value = "用户申请组织")
    private String applyOrgName;

    /**
     * 接待人ID
     */
    @ApiModelProperty(value = "接待人ID")
    private String receptionPersonId;

    /**
     * 接待人姓名
     */
    @ApiModelProperty(value = "接待人姓名")
    private String receptionPersonName;


    /**
     * 接待人联系方式
     */
    @ApiModelProperty(value = "接待人联系方式")
    private String receptionPersonPhone;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime cTime;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private String staus;

    /**
     * 食堂id
     */
    @ApiModelProperty(value = "食堂id")
    private String applyRestaurantId;


    /**
     * 食堂
     */
    @ApiModelProperty(value = "食堂")
    private String applyRestaurantName;

    @ApiModelProperty(value = "审批")
    private AppTFlowdetail appTFlowdetail;




}
