package com.property.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Trepaircheck {
    private String checkId;

    private String repairId;

    private String checkPerson;

    private Integer status;

    private Date checkDate;

    private Date cTime;

    private Date uTime;

    private String checkPersonCode;

}