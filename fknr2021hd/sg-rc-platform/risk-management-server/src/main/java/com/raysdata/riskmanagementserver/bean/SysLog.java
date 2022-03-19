package com.raysdata.riskmanagementserver.bean;
import lombok.Data;

@Data
public class SysLog {
  private String id;
  private String logContent;
  private String moduleName;
  private String logIp;
  private String operationType;
  private String logOn;
  private String logUserName;
  private String logLevel;
  private String dataType;
  private String errorType;
  private java.sql.Timestamp logTime;
  private String logName;
  private String logUserId;
  private String reviewState;
}
