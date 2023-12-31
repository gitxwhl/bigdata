package com.mstdemo.mst.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Tarea {

  private String id;
  private String name;
  private String code;
  private String parentCode;
  private String fullName;
  private String level;
  //子节点
  List<Tarea> childList=new ArrayList();




}
