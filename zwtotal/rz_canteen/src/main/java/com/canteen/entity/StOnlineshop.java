package com.canteen.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StOnlineshop {

  private Integer id;
  /*
   *  父级（不限）
   */
  private String parentIds;
  /**
   * 分类编码
   */
  private String no;
  /**
   * 分类名称
   */
  private String branchname;
  /**
   * 名称
   */
  private String name;
  /**
   * 重量
   */
  private String weight;
  /**
   * 简介
   */
  private String introduction;
  /**
   * 价格
   */
  private String price;
  /**
   * 数量
   */
  private String number;
  /**
   * 单位
   */
  private String company;
  /**
   * 购物车外键
   */
  private String shop;
  /**
   * 运维餐厅外键
   */
  private String restaurant;

}