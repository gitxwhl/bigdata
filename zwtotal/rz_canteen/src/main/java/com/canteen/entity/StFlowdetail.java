package com.canteen.entity;




import java.io.Serializable;

/**
 * @Auther: lilong
 * @String: 2020/11/11 15:08
 * @Description:
 */
public class StFlowdetail   implements Serializable{
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



    @Override
    public String toString() {
        return "AppTFlowdetail{" +
                "id=" + id +
                ", FlowNo=" + FlowNo +
                ", AuditUserID=" + AuditUserID +
                ", AuditUserName=" + AuditUserName +
                ", AuditRemark=" + AuditRemark +
                ", AuditTime=" + AuditTime +
                ", AuditStatus=" + AuditStatus +
                "}";
    }
}