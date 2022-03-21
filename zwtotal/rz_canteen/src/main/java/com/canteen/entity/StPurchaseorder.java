package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class StPurchaseorder {
    private Integer id;
    
    private String name;

    private String no;

    private String type;

    private String timestamp;

    private String supplier;

    private String purchasingunit;

    private String detailed;

    private String state;

}