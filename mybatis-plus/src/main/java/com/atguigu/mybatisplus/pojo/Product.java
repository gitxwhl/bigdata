package com.atguigu.mybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

@Data
public class Product {
  @TableId("id")
  private Long id;
  private String name;
  private Long price;
  @Version
  private Long version;

}
