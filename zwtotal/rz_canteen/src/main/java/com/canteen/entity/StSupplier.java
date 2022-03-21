package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StSupplier {
    private Integer id;
    private String name;    //名称
    private String number;  //编号
    private String code;    //统一社会信用代码
    private String establishTime;   //成立时间
    private String registeredCapital;   //注册资本
    private String address; //企业地址
    private String businessValidity;    //营业执照有效期
    private String representative;  //法人代表
    private String phone;   //法人电话
    private String idCard;  //身份证件
    //private String state;   //准入状态
    private String reviewResults;   //评审结果
    private String selection;   //入选情况
    //private String integralEvaluate;    //积分
    //private String punished;    //是否被惩罚
    //private String blacklist;   //是否为黑名单
    private String payableSurplus;  //剩余应付账款
    //private String scoreId;  //评分外键
    //private String certificateId;  //资质证明外键
    //private String penaltyListId;  //惩罚详情外键
    //private String blacklistId;  //黑名单外键
    private String certificates;  //资质文件
    private List files;
    private String beginTime;   //开始时间
    private String endTime; //解除时间
    private String explains; //说明
    private String state; //状态(默认:0 备选:1 选定:2)
    private String includedTime; //入选时间

}
