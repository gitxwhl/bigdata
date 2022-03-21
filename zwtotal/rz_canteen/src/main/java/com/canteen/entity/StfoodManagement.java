package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class StfoodManagement {
    private Integer id;

    private String dishCode;    //菜品编号

    private String dishName;    //名称

    private String dishCategory;    //菜品类别

    private String referencePrice;  //参考价格

    private String company;    //单位

    private String weight;  //重量

    private String ingredients;    //食材配料

    private String state;    //状态（0正常，1停用）

    private String remarks; //备注

    private String delFlag;    //逻辑删除标记（0：显示；1：隐藏）

    private String foodPictures;    //菜品图片地址

    private byte[] pic;

    private String energy;  //能量

    private String dishesCategory;  //菜品分类

    private String meals;   //餐别

    private String taste;   //口味

    private String online;  //线上菜品外键

    private String degree;    //辣度

    private String feature;    //是否为本地特色（0:是 1：否）

    private String careful;    //特殊人群慎选

    private String createTime;  //创建时间

}