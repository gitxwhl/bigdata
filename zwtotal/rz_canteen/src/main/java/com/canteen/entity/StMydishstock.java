package com.canteen.entity;

import lombok.Data;

import java.util.Date;

/**
 * 不对外开放，不写swagger注解
 */
@Data
public class StMydishstock {

  private int id;

  /**
   * 菜品id
   */
  private int disId;
  /**
   * 入库库存（可选择部分上架）
   */
  private int totolStock;
  /**
   * 上架库存（可用库存+锁定库存
   */
  private int onlineStock;
  /**
   * 未支付订单锁定库存
   */
  private int lockStock;
  /**
   * 历史消耗库存；历史入库数量之和
   */
  private int historyStock;
  /**
   * 可用库存
   */
  private int avaStock;
  private Date insertDate;

}
