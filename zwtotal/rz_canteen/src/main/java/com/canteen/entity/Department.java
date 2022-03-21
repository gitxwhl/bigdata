package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Department {
  /**
   * 部门ID
   */
  private String id;
  /**
   * 部门名称
   */
  private String dname;
  /**
   * 所属组织ID
   */
  private String organizationId;
  /**
   * 部门所属域ID
   */
  private String domainId;
  /**
   * 父部门ID
   */
  private String parentId;
  /**
   * 部门ID
   */
  private String fullId;
  /**
   * 全名称,如：/集团/事业部/研发部
   */
  private String fullName;
  /**
   * 1默认，-1非默认
   */
  private long isDefault;
  /**
   */
  private String note;
  /**
   */
  private String sate;



}
