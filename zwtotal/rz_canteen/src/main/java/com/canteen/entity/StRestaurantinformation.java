package com.canteen.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class StRestaurantinformation {


    private Integer id;
    private String dictionary;
    private String supplystarttime;
    private String supplyendtime;
    private String givestarttime;
    private String giveendtime;
    private String upperstarttime;
    private String upperendtime;
    private String dishes;
    private String mealintroduction;
    private String restaurant;


}
