package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class StMeasurechoicedishes {
    private Integer id;

    //计量设备
    private String meteringEquipment;

    //菜品名称
    private String dishName;

    //所属餐厅
    private String restaurant;

    //数量
    private String numbers;

    //重量
    private String weight;

    //参考价格
    private Double referencePrice;

    //备注
    private String remarks;

    private String delFlag;
}
