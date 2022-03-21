package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class StFoodIngredients {
  private long id;
  private String name;
  private String code;
  private String category;
  private String place;
  private String edible;
  private String water;
  private String energy;
  private String protein;
  private String fat;
  private String carbohydrate;
  private String dietaryFiber;
  private String cholesterol;
  private String ashContent;
  private String calcium;
  private String magnesium;
  private String copper;
  private String phosphorus;
  private String iron;
  private String manganese;
  private String potassium;
  private String zinc;
  private String iodine;
  private String sodium;
  private String selenium;
  private String vitaminA;
  private String vitaminE;
  private String retinol;
  private String vitaminK;
  private String carotene;
  private String vitaminD;
  private String vitaminB1;
  private String vitamin_B12;
  private String pantothenicAcid;
  private String vitaminB2;
  private String folicAcid;
  private String vitaminC;
  private String nicotinicAcid;
  private String choline;
  private String vitaminB6;
  private String biotin;
  /**
   * 采购订单外键
   */
  private String order;

  /**
   * 菜品外键
   */
  private String dishes;
  /**
   * 供应商外键
   */
  private String supplierId;

}
