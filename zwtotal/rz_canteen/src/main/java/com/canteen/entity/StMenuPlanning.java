package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StMenuPlanning {
    private int id;
    private String meals;
    private String restaurant;
    private String menu;
    private String orderBeginTime;
    private String orderEndTime;
    private String refundTime;
    private String mealDate;
    private String state;
}
