package com.officeServices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class user {
    /**
     *
     */
    private String dID;
    /**
     *帐户
     */
    private String account;
    /**
     *密码
     */
    private String password;
    /**
     *姓名
     */
    private String name;
    /**
     *性别
     */
    private String sex;
    /**
     *组织id
     */
    private String id;
    /**
     *部门id
     */
    private String organizationId;
    /**
     *部门编号
     */
    private String domainId;

    /**
     *电话
     */
    private String departmentId;
    /**
     *邮箱
     */
    private String mobile;
    /**
     *邮箱
     */
    private String email;
    /**
     *用户类型
     */
    private String userType;
    /**
     *
     */
    private String salt;
    /**
     *密码时间
     */
    private String  passwordTime;
    /**
     *最后一次
     */
    private String lastTime;
    /**
     *创建者
     */
    private String createUser;
    /**
     *创建时间
     */
    private String createTime;
    /**
     *启用
     */
    private String enable;
    /**
     *
     */
    private String bpmUserID;
    /**
     *注意事项
     */
    private String npte ;





}
