package com.property.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Wymaintenance {
    private Integer id;

    private String cleaning;

    private String scoring;

    private String scoringitem;

    private String evaluationtime;


}