package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StMealmanagement {
    private Integer id;
    private String card;
    private String face;
    private String number;
    private String personnel;
    private String buffet;
}
