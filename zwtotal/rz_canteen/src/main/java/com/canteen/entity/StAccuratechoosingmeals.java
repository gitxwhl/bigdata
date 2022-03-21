package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class StAccuratechoosingmeals {
    /** */
    private Integer id;

    /** 蛋白质*/
    private String protein;

    /** 脂肪*/
    private String fat;

    /** 饱和脂肪酸*/
    private String fattyacids;

    /** 胆固醇*/
    private String cholesterol;

    /** 碳水化合物*/
    private String carbohydrate;

    /** 膳食纤维*/
    private String dietaryfiber;

    /** 维生素A*/
    private String vitamina;

    /** 维生素D*/
    private String vitamind;

    /** 维生素E*/
    private String vitamine;

    /** 维生素K*/
    private String vitamink;

    /** 维生素B1*/
    private String vitaminb1;

    /** 维生素B2*/
    private String vitaminb2;

    /** 维生素B6*/
    private String vitaminb6;

    /** 维生素B12*/
    private String vitamin12;

    /** 维生素C*/
    private String vitaminc;

    /** 烟酸*/
    private String nicotinicacid;

    /** 叶酸*/
    private String folicacid;

    /** 泛酸*/
    private String pantothenicacid;

    /** 生物素*/
    private String biotin;

    /** 胆碱*/
    private String choline;

    /** 钙*/
    private String calcium;

    /** 磷*/
    private String phosphorus;

    /** 钾*/
    private String potassium;

    /** 钠*/
    private String sodium;

    /** 镁*/
    private String magnesium;

    /** 铁*/
    private String iron;

    /** 锌*/
    private String zinc;

    /** 碘*/
    private String iodine;

    /** 硒*/
    private String selenium;

    /** 铜*/
    private String copper;

    /** 氟*/
    private String fluorine;

    /** 铬*/
    private String chromium;

    /** 锰*/
    private String manganese;

    /** 钼*/
    private String molybdenum;

    /** 运维餐厅外键*/
    private String restaurant;

    /** 餐盘种类外键*/
    private String platetype;

    /** 重量*/
    private String weight;

    /** 能量*/
    private String energy;

    /** 状态*/
    private String state;
}
