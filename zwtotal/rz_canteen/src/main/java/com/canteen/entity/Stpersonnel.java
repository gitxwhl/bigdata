package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 人员信息
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Stpersonnel {

    private Integer id;
    /**
     *人员名称
     */
    private String name;
    /**
     *编码
     */
    private String no;
    /**
     *人员类型
     */
    private String type;
    /**
     *手机号
     */
    private String phone;
    /**
     *职级
     */
    private String rank;
    /**
     *性别
     */
    private String sex;
    /**
     *出生年月
     */
    private String birthday;
    /**
     *运维餐厅外键
     */
    private String restaurant;

    /**
     *体检指标
     */
    List<Stbodyproject> stbodyprojects;
    /**
     *身体状态（0.正常   1.不正常）
     */
    private String state;





}
