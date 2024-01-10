package com.mstdemo.mst.bean;

import lombok.Data;

@Data
public class Student {

  private String sid;
  private String sname;
  private String sex;
  private String height;
  private String classa;
  private String birthday;
  private String hometown;
  private String score;
  private String subjct;
  public Student(String sid,String sname){
    this.sid=sid;
    this.sname=sname;

  }



}
