package com.raysdata.riskmanagementserver.bean.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * * Copyright ©2003- 2020   State Grid Corporation of China, All Rights Reserved.
 * Copyright: NARI
 *
 * @Project: 安全生产风险管控平台 bean 基类
 * @Version：V1.0
 * @Package: com.rmcp.base.service.entity
 * @Author: zhouyanbin
 * @Create: 4/26/20 10:48 PM
 * @Description: this file is used for ...
 * @History: modify
 **/
public class BaseEntity {

    @ChangeLog(fieldName = "createTimestamp", alias = "createTime") //创建时间
    private Date createTimestamp;
    @ChangeLog(fieldName = "updateTimestamp", alias = "updateTime") //更新时间
    private Date updateTime;
    @ChangeLog(fieldName = "dataFillOrg",alias = "datafillOrg")
    private String dataFillOrg;   //数据填报单位
    @ChangeLog(fieldName = "dataFillOrgId" ,alias = "datafillOrgId")
    private String dataFillOrgId;   //数据填报单位ID
    @ChangeLog(fieldName = "dataReportOrg",alias = "datareportOrg")
    private String dataReportOrg;   //数据上报单位
    @ChangeLog(fieldName = "dataReportOrgId",alias = "datareportOrgId")
    private String dataReportOrgId;   //数据上报单位ID
    @ChangeLog(fieldName = "dataCommonId",alias = "datacommonId")
    private String dataCommonId;   //同一条数据公共ID

    //当前用户id
    private String currentUserId;
    private String currentUserName;

    public String getCurrentUserName() {
        return currentUserName;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    private List<Integer> ids = new ArrayList<Integer>();

    public Date getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public Date getUpdateTimestamp() {
        return updateTime;
    }

    public void setUpdateTimestamp(Date updateTimestamp) {
        this.updateTime = updateTimestamp;
    }

    public String getDataFillOrg() {
        return dataFillOrg;
    }

    public void setDataFillOrg(String dataFillOrg) {
        this.dataFillOrg = dataFillOrg;
    }

    public String getDataFillOrgId() {
        return dataFillOrgId;
    }

    public void setDataFillOrgId(String dataFillOrgId) {
        this.dataFillOrgId = dataFillOrgId;
    }

    public String getDataReportOrg() {
        return dataReportOrg;
    }

    public void setDataReportOrg(String dataReportOrg) {
        this.dataReportOrg = dataReportOrg;
    }

    public String getDataReportOrgId() {
        return dataReportOrgId;
    }

    public void setDataReportOrgId(String dataReportOrgId) {
        this.dataReportOrgId = dataReportOrgId;
    }

    public String getDataCommonId() {
        return dataCommonId;
    }

    public void setDataCommonId(String dataCommonId) {
        this.dataCommonId = dataCommonId;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }
}
