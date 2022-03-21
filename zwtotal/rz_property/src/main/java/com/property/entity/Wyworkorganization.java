package com.property.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Wyworkorganization {
    private Integer id;

    private String name;

    private String plantime;

    private String content;

    private String supplier;

    private String registrationtype;

    private String delFlag;


}