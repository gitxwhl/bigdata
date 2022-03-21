package com.property.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LysbGzGuzbxsq {
    //维修申请ID
    private String ID;

    //备注
    private String BEIZ;

    //单位ID
    private String CORPID;

    //维修工单附件
    private String FUJ;

    //审核状态（状态默认未提交  01：未提交，02：待审核，03：待维修，04：审核不通过，05：维修完成，06：无维修价值，
    // 07：已申请退役，08：待分配，09：维修中）
    private String SHENHZT;

    //申请类型 （01：系统接收，02：手工录入）
    private String SHENQLX;

    //申请理由
    private String SHENQLY;

    //申请人ID
    private String SHENQRID;

    //申请人名称
    private String SHENQRMC;

    //申请时间
    private String SHENQSJ;

    //维修工单号
    private String WEIXGDH;

    //楼宇设备ID
    private String WEIXSB;

    //审核不通过原因
    private String BUTGYY;

    //维修人员
    private String WEIXRY;

    //维修开始时间
    private String WEIXKSSJ;

    //维修结束时间
    private String WEIXJSSJ;

    //维修人员名称
    private String WEIXRYNAME;

    //维修人员账户名
    private String WEIXRYCODE;

    private String zhicyz;  //

    //申请人电话
    private String shenqrdh;
}
