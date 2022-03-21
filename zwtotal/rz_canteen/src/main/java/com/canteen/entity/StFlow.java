package com.canteen.entity;





import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Auther: lilong
 * @Date: 2020/11/11 14:39
 * @Description:
 */

public class StFlow   {

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


    public String getFlowNo() {
        return FlowNo;
    }

    public void setFlowNo(String FlowNo) {
        this.FlowNo = FlowNo;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getBusType() {
        return BusType;
    }

    public void setBusType(String BusType) {
        this.BusType = BusType;
    }

    public String getAddUserNo() {
        return AddUserNo;
    }

    public void setAddUserNo(String AddUserNo) {
        this.AddUserNo = AddUserNo;
    }

    public LocalDateTime getAddTime() {
        return AddTime;
    }

    public void setAddTime(LocalDateTime AddTime) {
        this.AddTime = AddTime;
    }

    public Integer getApproStatus() {
        return ApproStatus;
    }

    public void setApproStatus(Integer ApproStatus) {
        this.ApproStatus = ApproStatus;
    }



    @Override
    public String toString() {
        return "AppTFlow{" +
                "FlowNo=" + FlowNo +
                ", Title=" + Title +
                ", BusType=" + BusType +
                ", AddUserNo=" + AddUserNo +
                ", AddTime=" + AddTime +
                ", ApproStatus=" + ApproStatus +
                "}";
    }

}