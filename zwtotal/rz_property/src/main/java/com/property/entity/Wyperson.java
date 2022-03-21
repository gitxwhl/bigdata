package com.property.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Wyperson {
    private Integer id;

    private String name;    //姓名

    private String post;    //岗位

    private String telephone;   //电话

    private String photo;   //照片

    private String department;  //部门外键

    private String isgate;  //是否为门岗人员(0:是  1:不是)


}