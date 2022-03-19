package com.raysdata.riskmanagementserver.bean.vo;

import lombok.Data;

@Data
public class SrpRiskGridwannoticeVo {
    private Integer page;
    private Integer size;
    private String title;
    private String warningLevel;
    private String warnNum;
    private String publishOrg;
    private String warnStatus;
    private String startTime;
    private String endTime;
    private Integer offset;
}
