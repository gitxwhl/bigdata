package com.property.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 公司部门表
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Department implements Serializable {
    /**
     *部门id
     */
    private String id;
    /**
     *部门名称
     */
    private String name;
    /**
     *所属组织ID
     */
    private String  organizationId;
    /**
     *部门所属域ID
     */
    private String domainId;
    /**
     *父部门ID
     */
    private String ParentId;

    /**
     *全ID,如：/100001/100002/100003
     */
    private String fullId;
    /**
     *全名称,如：/集团/事业部/研发部
     */
    private String fullName;
    /**
     *1默认，-1非默认
     */
    private String isDefault;
    private String note;
    /**
     *菜单等级
     */
    private String lev;



    List<Department> Subclass  = new ArrayList<>();


}
