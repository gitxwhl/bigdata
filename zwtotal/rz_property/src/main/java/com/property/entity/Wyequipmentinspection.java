package com.property.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Wyequipmentinspection {
    private Integer id;

    private String inspection;

    private String incequipment;

    private String plantime;

    private String planendtime;

    private String planner;

    private String begintime;

    private String endtime;

    private String inspectionperson;

    private String results;

    private String remarks;

    private String missing;

    private String company;

    private String missingequipment;


}