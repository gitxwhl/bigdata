package com.raysdata.riskmanagementserver.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
@AllArgsConstructor
@ApiModel("用户实体类 PtUaUser")
public class PtUaUser {
  @ApiModelProperty(value ="用户ID")
  private String id;
  @ApiModelProperty(value ="用户ID")
  private String userId;
  @ApiModelProperty(value ="标准组织单元ID")
  private String userBaseorgunitid;
  @ApiModelProperty(value ="用户名")
  private String userName;
  @ApiModelProperty(value ="用户密码")
  private String userPwd;
  @ApiModelProperty(value ="用户姓名")
  private String userFullname;
  @ApiModelProperty(value ="用户状态")
  private Integer userStatus;
  @ApiModelProperty(value ="用户编号")
  private String userNumber;
  @ApiModelProperty(value ="用户类型")
  private Integer userType;
  @ApiModelProperty(value ="允许访问网络类型")
  private Integer userNettype;
  @ApiModelProperty(value ="用户生日")
  private String userBirth;
  @ApiModelProperty(value ="用户性别")
  private Integer userSex;
  @ApiModelProperty(value ="用户身份证号")
  private String userCard;
  @ApiModelProperty(value ="用户手机号")
  private String userPhone;
  @ApiModelProperty(value ="用户邮箱")
  private String userEmail;
  @ApiModelProperty(value ="用户解锁时间")
  private String userUnlocktime;
  @ApiModelProperty(value ="允许登录的IP段")
  private String userIp;
  @ApiModelProperty(value ="允许登录起始时间")
  private String userStime;
  @ApiModelProperty(value ="允许登录结束时间")
  private String userEtime;
  @ApiModelProperty(value ="用户创建时间")
  private String userCtime;
  @ApiModelProperty(value ="用户修改时间")
  private String userMtime;
  @ApiModelProperty(value ="上一次密码修改时间")
  private String userPmtime;
  @ApiModelProperty(value ="上一次登录时间")
  private String userLtime;
  @ApiModelProperty(value ="扩展字段")
  private String userExt;
  @ApiModelProperty(value ="用户图片")
  private String userImage;
  private long userSource;
  @ApiModelProperty(value ="用户前三次修改的密码")
  private String userThreepassword;
  @ApiModelProperty(value ="用户绑定MAC")
  private String userMac;
}
