package com.raysdata.riskmanagementserver.bean.vo;

import lombok.Data;

@Data
public class SrpRiskIndusriskWarnnoticeVo {
    private String startTime;
    private String endTime;
    private String title;
    private String warningLevel;
    private String warnNum;
    private String publishOrg;
    private String warnStatus;
    private String page;
    private Integer size;
    private Integer offset;
}
