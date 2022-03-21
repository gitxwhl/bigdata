package com.property.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Wyinspectionplan {
    private Integer id;

    private String place;

    private String inspectiontask;

    private String schedule;

    private String personnel;

    private String content;

    private String delFlag;
}