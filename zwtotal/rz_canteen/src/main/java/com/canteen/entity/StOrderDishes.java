package com.canteen.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class StOrderDishes {

  @ApiModelProperty(value="系统id,新增不传")
  private int id;
  @ApiModelProperty(value="订单编号，新增不传")
  private String orderNo;
  @ApiModelProperty(value="下单物品id")
  private int dishId;
  @ApiModelProperty(value="物品数量")
  private String dishNum;



}
