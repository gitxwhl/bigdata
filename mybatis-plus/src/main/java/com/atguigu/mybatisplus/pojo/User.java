package com.atguigu.mybatisplus.pojo;

import com.atguigu.mybatisplus.enums.SexEnum;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
//设置实体类对应的表名
@TableName("t_user")
public class User {
  //将属性指定的字段指定为主键
  //数据库主键字段和实体类字段不对应时，@TableId 注解的value属性用于指定主键的字段,为id或者uid
  //设置主键为自动递增
//  @TableId(value = "id",type = IdType.AUTO)
  /**
   * IdType.ASSIGN_ID   基于雪花算法的策略生成数据id，与数据库id设置自增无关
   * IdType.AUTO     使用数据库自增策略，该类型需要确保数据库设置了id自增，否则无效
   */
//  @TableId(value = "uid",type = IdType.ASSIGN_ID)
  @TableId("uid")
  private Long uid;
  //指定属性对应的字段名
  @TableField("user_name")
  private String name;
  private long age;
  private String emial;
  //逻辑删除  自动将字段为0 的改为1
  @TableLogic
  private String isDel;
  //性别
  private SexEnum sex;



}
