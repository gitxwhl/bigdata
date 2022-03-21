package com.property.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Wyaccident {
    private Integer id;

    private String event;

    private String loss;

    private String timestamp;

    private String place;

    private String involved;

    private String reporter;

    private String company;


}