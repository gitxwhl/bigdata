package com.mashibing.dto;
import lombok.Data;

@Data
public class PassengerUser {

  private Long id;
  //创建时间
  private String gmtCreate;
//  修改时间
  private String gmtModified;
//  手机号
  private String passengerPhone;
//  乘客名
  private String passengerName;
//  性别
  private long passengerGender;
//  乘客状态
  private long state;


}
