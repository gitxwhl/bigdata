package com.property.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Wymaintainorder {
    private Integer id;

    private String workno;

    private String state;

    private String policy;

    private Date generationtime;

    private Date forcetime;

    private String personnel;

    private String receiving;

    private String armstatus;


}