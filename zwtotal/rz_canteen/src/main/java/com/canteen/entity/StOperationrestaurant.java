package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class StOperationrestaurant {
    private String id;
    /**
     *父级
     */
    private String parentIds;


    /**
     *餐厅名称
     */
    private String restaurantName;

    /**
     *餐厅编码
     */
    private String restaurantCode;
    /**
     * 等级
     */
    private Integer decide;
    private String capacity;
    private String introduction;
    /**
     * （0：超市 1：餐厅）
     */
    private String deFlag;

    List<StOperationrestaurant> Subclass  = new ArrayList<>();
}
