package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor

public class StMydishes {

    private Integer id;
    /**
     *名称
     */
    private String name;
    /**
     *类别（类别外建）
     */
    private String category;
    /**
     *参考价格
     */
    private Double referenceprice;
    /**
     *重量
     */
    private String weight;

    /**
     * 能量
     */
    private String energy;
    /**
     *备注
     */
    private String remarks;
    /**
     *运维餐厅（运维餐厅外键）
     */
    private String restaurant;
    /**
     *餐别
     */
    private String meals;

    /**
     * 菜品图片地址'
     */
    private String foodpictures;
    /**
     *菜品类别
     */
    private String dishescategory;
    /**
     *口味
     */
    private  String taste;
    /**
     *线上菜品外键
     */
    private  String online;
    /**
     *菜品管理编码
     */
    private String dishcode;
    /**
     *餐厅还是超市 0餐厅 1超市' 字段暂时未使用
     */
    private  String classtype;

    /**
     *评分
     */
    private  String score;

}
