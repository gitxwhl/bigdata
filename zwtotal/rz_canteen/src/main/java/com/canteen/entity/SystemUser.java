package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

/**
 * 用户
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class SystemUser implements Serializable {
    /**
     * 用户id
     */
    private String id;
    /**
     * 用户name
     */
    private String name;
    /**
     * 登录姓名
     */
    private String loginName;
    /**
     * 登录密码
     */
    private String loginPassword;
    /**
     * 登录时间
     */
    private String loginDate;
    /**
     * 登录ip
     */
    private String loginIp;
    /**
     * 手机号
     */
    private String phone;

    /**
     * 备注信息
     */
    private String remarks;

    /**
     * 删除标记（逻辑删除应用）
     */
    private String del_flag;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 用户头像
     */
    private String photo;
    /**
     * 角色id(外键)
     */
    private String roleId;/**
     * 登录状态（状态信息：0、正常 1、禁用）
     */
    private String loginState;
    /**
     * 归属部门（部门外键）
     */
    private String officeId;
    /**
     *出生年月
     */
    private String bithiDay;
    /**
     * 性别
     */
    private String sex;
    /**
     * 编号
     */
    private String no;
    /**
     *体检指标
     */
    List<Stbodyproject> stbodyprojects;






}
