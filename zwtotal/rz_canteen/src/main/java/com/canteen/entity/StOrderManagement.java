package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StOrderManagement {
    private Integer id;
    private String orderNumber;
    private String reservePersonnel;
    private String telephone;
    private String restaurant;
    private String orderTime;
    private String scheduled;
    private String dictionary;
    private String costTotal;
    private String pickTime;
    private String deduction;
    private String state;
    private String details;
    private  String  ordertype;
}
