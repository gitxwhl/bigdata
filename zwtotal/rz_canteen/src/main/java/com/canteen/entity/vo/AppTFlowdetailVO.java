package com.canteen.entity.vo;

import java.io.Serializable;

/**
 * @Auther: lilong
 * @Date: 2020/11/23 10:53
 * @Description:
 */
public class AppTFlowdetailVO  implements Serializable {
    private static final long serialVersionUID=1L;

    /**
     * 主键
     */

    private String id;

    /**
     * 审批编号，关联主表
     */
    private String FlowNo;

    /**
     * 审核人id
     */
    private String AuditUserID;

    /**
     * 审核人姓名
     */
    private String AuditUserName;

    /**
     * 备注
     */
    private String AuditRemark;

    /**
     * 审批时间
     */
    private String AuditTime;

    /**
     * 审批状态
     */
    private String AuditStatus;

    /**
     * 原因
     */
    private String reason;
    /**
     * 异地就餐申请主表id
    **/
    private String id2;
    /**
     * 申请人
    **/
    private String userName;
    /**
     * 申请人id
    **/
    private String userId;
    /**
     * 单位id
     **/
    private String orgId;
    /**
     * 单位名称
     **/
    private String orgName;
    /**
     * 部门id
     **/
    private String deptId;
    /**
     * 部门id
     **/
    private String deptName;

    /**
     * 异地就餐申请主表状态
     **/
    private String staus;
    /**
     * 接待人
     **/
    private String receptionPersonName;

    /**
     * 到访单位名称
     **/
    private String applyOrgName;
    /**
     * 申请时间
     **/
    private String c_time;
    /**
     * 开始时间
     **/
    private String start_time;

    /**
     * 结束时间
     **/
    private String end_time;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getFlowstatus() {
        return flowstatus;
    }

    public void setFlowstatus(String flowstatus) {
        this.flowstatus = flowstatus;
    }

    /**
     *流程节点
     */
    private String flowstatus;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlowNo() {
        return FlowNo;
    }

    public void setFlowNo(String FlowNo) {
        this.FlowNo = FlowNo;
    }

    public String getAuditUserID() {
        return AuditUserID;
    }

    public void setAuditUserID(String AuditUserID) {
        this.AuditUserID = AuditUserID;
    }

    public String getAuditUserName() {
        return AuditUserName;
    }

    public void setAuditUserName(String AuditUserName) {
        this.AuditUserName = AuditUserName;
    }

    public String getAuditRemark() {
        return AuditRemark;
    }

    public void setAuditRemark(String AuditRemark) {
        this.AuditRemark = AuditRemark;
    }

    public String getAuditTime() {
        return AuditTime;
    }

    public void setAuditTime(String AuditTime) {
        this.AuditTime = AuditTime;
    }

    public String getAuditStatus() {
        return AuditStatus;
    }

    public void setAuditStatus(String AuditStatus) {
        this.AuditStatus = AuditStatus;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getStaus() {
        return staus;
    }

    public void setStaus(String staus) {
        this.staus = staus;
    }

    public String getReceptionPersonName() {
        return receptionPersonName;
    }

    public void setReceptionPersonName(String receptionPersonName) {
        this.receptionPersonName = receptionPersonName;
    }

    public String getApplyOrgName() {
        return applyOrgName;
    }

    public void setApplyOrgName(String applyOrgName) {
        this.applyOrgName = applyOrgName;
    }

    public String getC_time() {
        return c_time;
    }

    public void setC_time(String c_time) {
        this.c_time = c_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    @Override
    public String toString() {
        return "AppTFlowdetailVO{" +
                "id='" + id + '\'' +
                ", FlowNo='" + FlowNo + '\'' +
                ", AuditUserID='" + AuditUserID + '\'' +
                ", AuditUserName='" + AuditUserName + '\'' +
                ", AuditRemark='" + AuditRemark + '\'' +
                ", AuditTime='" + AuditTime + '\'' +
                ", AuditStatus='" + AuditStatus + '\'' +
                ", reason='" + reason + '\'' +
                ", id2='" + id2 + '\'' +
                ", userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", orgName='" + orgName + '\'' +
                ", deptId='" + deptId + '\'' +
                ", deptName='" + deptName + '\'' +
                ", staus='" + staus + '\'' +
                ", receptionPersonName='" + receptionPersonName + '\'' +
                ", applyOrgName='" + applyOrgName + '\'' +
                ", c_time='" + c_time + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", flowstatus='" + flowstatus + '\'' +
                '}';
    }

}