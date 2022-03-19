package com.raysdata.riskmanagementserver.bean;

import lombok.Data;

@Data
public class SrpRiskTransforworkplan {
  private String gridwarntoworkplanId;
  private String gridconstwarnnoticeId;
  private String workPlanId;
  private java.sql.Timestamp createTime;
  private java.sql.Timestamp updateTime;
  private String datafillOrg;
  private String datafillOrgId;
  private String datareportOrg;
  private String datareportOrgId;
  private String datacommonId;

}
