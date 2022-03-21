package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  /**
   * 用户ID
   */
  private long did;
  /**
   * 账号名称
   */
  private String account;
  /**
   * 密码
   */
  private String password;
  /**
   */
  private String passwdapp;
  /**
   * 用户名
   */
  private String name;
  /**
   * 性别:1男，2女
   */
  private long sex;
  /**
   * 所属组织ID
   */
  private String organizationId;
  /**
   * 所属域ID
   */
  private String domainId;
  /**
   * 部门ID
   */
  private String departmentId;
  /**
   * 手机号
   */
  private String mobile;
  /**
   * 邮件地址
   */
  private String email;
  /**
   * 用户类型
   */
  private long userType;
  /**
   * 盐
   */
  private String salt;
  /**
   * 密码修改时间
   */
  private long passwordTime;
  /**
   * 最后一次登录时间
   */
  private long lastTime;
  /**
   * 记录创建者
   */
  private String createUser;
  /**
   * 记录创建时间
   */
  private long createTime;
  /**
   * 是否启用
   */
  private long enable;
  /**
   * BPM对应用户ID
   */
  private String bpmUserId;
  /**
   *
   */
  private String note;
  /**
   *
   */
  private String permission;
  /**
   *
   */
  private String officeTel;
  /**
   *
   */
  private String roleid;
  /**
   *
   */
  private String isexpand;
  /**
   *
   */
  private String isDraw;
  /**
   *
   */
  private String position;
  /**
   *
   */
  private java.sql.Timestamp birthday;
  /**
   *
   */
  private String identity;
  /**
   *
   */
  private String imgUrl;
  /**
   *部门信息
   */
  private Department department;


}
