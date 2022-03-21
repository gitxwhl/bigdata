package com.officeServices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class TstockPlanRecordMain {

    /**
     *
     */
    private String id;
    /**
     *订单编号
     */
    private String applyNo;
    /**
     *
     */
    private String operatePersonId;
    /**
     *
     */
    private String operatePersonName;
    /**
     *操作时间
     */
    private String operateTime;
    /**
     *图片地址
     */
    private String pictureUrl;
    /**
     *备注
     */
    private String remark;
    /**
     *0：入库记录  1：上架记录
     */
    private String isUp;

}
