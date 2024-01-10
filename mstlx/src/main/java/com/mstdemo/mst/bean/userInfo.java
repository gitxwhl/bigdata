package com.mstdemo.mst.bean;

import lombok.Data;

@Data
public class userInfo {
  private String userId;
  private String userName;
  private String userPassword;
  private long userRole;
  private String deptId;

}
