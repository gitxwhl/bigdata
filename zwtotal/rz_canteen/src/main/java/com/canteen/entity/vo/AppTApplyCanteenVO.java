package com.canteen.entity.vo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;


@ApiModel(value="异地就餐参数",description="异地就餐对象")
public class AppTApplyCanteenVO implements Serializable {

    private static final long serialVersionUID=1L;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;


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


    public String getApplyRestaurantId() {
        return applyRestaurantId;
    }

    public void setApplyRestaurantId(String applyRestaurantId) {
        this.applyRestaurantId = applyRestaurantId;
    }

    public String getApplyRestaurantName() {
        return applyRestaurantName;
    }

    public void setApplyRestaurantName(String applyRestaurantName) {
        this.applyRestaurantName = applyRestaurantName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 审批人ID
     */
    @ApiModelProperty(value = "审批人ID")
    private String approvelPersonId;

    /**
     * 审批人
     */
    @ApiModelProperty(value = "审批人")
    private String approvelPersonName;

    public String getApprovelPersonId() {
        return approvelPersonId;
    }

    public void setApprovelPersonId(String approvelPersonId) {
        this.approvelPersonId = approvelPersonId;
    }

    public String getApprovelPersonName() {
        return approvelPersonName;
    }

    public void setApprovelPersonName(String approvelPersonName) {
        this.approvelPersonName = approvelPersonName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getApplyOrgId() {
        return applyOrgId;
    }

    public void setApplyOrgId(String applyOrgId) {
        this.applyOrgId = applyOrgId;
    }

    public String getApplyOrgName() {
        return applyOrgName;
    }

    public void setApplyOrgName(String applyOrgName) {
        this.applyOrgName = applyOrgName;
    }

    public String getReceptionPersonId() {
        return receptionPersonId;
    }

    public void setReceptionPersonId(String receptionPersonId) {
        this.receptionPersonId = receptionPersonId;
    }

    public String getReceptionPersonName() {
        return receptionPersonName;
    }

    public void setReceptionPersonName(String receptionPersonName) {
        this.receptionPersonName = receptionPersonName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getReceptionPersonPhone() {
        return receptionPersonPhone;
    }

    public void setReceptionPersonPhone(String receptionPersonPhone) {
        this.receptionPersonPhone = receptionPersonPhone;
    }
    public String toString() {
        return "AppTApplyCanteen{" +
                ", userId=" + userId +
                ", userName=" + userName +
                ", orgId=" + orgId +
                ", orgName=" + orgName +
                ", deptId=" + deptId +
                ", deptName=" + deptName +
                ", applyOrgId=" + applyOrgId +
                ", applyOrgName=" + applyOrgName +
                ", receptionPersonId=" + receptionPersonId +
                ", receptionPersonName=" + receptionPersonName +
                ", remark=" + remark +
                "}";
    }
}
