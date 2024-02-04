package com.msb.pojo;

import lombok.Data;

@Data
public class Employee {
  private long id;
  private String empno;
  private String name;
  private String sex;
  private Integer age;
  private String job;
  private Integer deptmentId;

}
