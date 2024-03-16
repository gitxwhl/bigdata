package com.mashibing.internalcommon.dto;

import lombok.Data;

@Data
public class PriceRule {
    //城市编码
    private String cityCode;
    //车辆类型
    private String vehicleType;
    //起步价
    private Double startFare;
    //起步里程
    private Integer startMile;
    //一公里多少钱
    private String unitPricePerMile;
    //一分钟多少钱
    private String unitPricePerMinute;






}
