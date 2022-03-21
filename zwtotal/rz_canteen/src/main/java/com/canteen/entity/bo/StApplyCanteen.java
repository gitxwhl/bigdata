package com.canteen.entity.bo;

import java.io.Serializable;

/**
 * @Auther: lilong
 * @String: 2020/11/11 15:37
 * @Description:
 */
public class StApplyCanteen  implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */

    private String id;

    /**
     * 用户id
     */

    private String userId;

    /**
     * 用户姓名
     */

    private String userName;

    /**
     * 用户联系方式
     */

    private String userPhone;

    /**
     * 用户所在组织ID
     */

    private String orgId;

    /**
     * 用户所在组织名称
     */

    private String orgName;

    /**
     * 用户部门ID
     */

    private String deptId;

    /**
     * 用户部门名称
     */

    private String deptName;

    /**
     * 用户申请组织ID
     */

    private String applyOrgId;



    private String applyOrgName;

    /**
     * 接待人ID
     */

    private String receptionPersonId;

    /**
     * 接待人姓名
     */

    private String receptionPersonName;


    /**
     * 接待人联系方式
     */

    private String receptionPersonPhone;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private String cTime;

    /**
     * 状态
     */
    private String staus;

    /**
     * 食堂id
     */
    private String applyRestaurantId;


    /**
     * 食堂
     */
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getcTime() {
        return cTime;
    }

    public void setcTime(String cTime) {
        this.cTime = cTime;
    }

    public String getStaus() {
        return staus;
    }

    public void setStaus(String staus) {
        this.staus = staus;
    }

    public String getReceptionPersonPhone() {
        return receptionPersonPhone;
    }

    public void setReceptionPersonPhone(String receptionPersonPhone) {
        this.receptionPersonPhone = receptionPersonPhone;
    }


    @Override
    public String toString() {
        return "AppTApplyCanteen{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName=" + userName +
                ", userPhone=" + userPhone +
                ", orgId=" + orgId +
                ", orgName=" + orgName +
                ", deptId=" + deptId +
                ", deptName=" + deptName +
                ", applyOrgId=" + applyOrgId +
                ", applyOrgName=" + applyOrgName +
                ", receptionPersonId=" + receptionPersonId +
                ", receptionPersonName=" + receptionPersonName +
                ", receptionPersonPhone=" + receptionPersonPhone +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", remark=" + remark +
                ", cTime=" + cTime +
                ", staus=" + staus +
                "}";
    }
}
