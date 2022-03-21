package com.property.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Wycleaningplan {
    private Integer id;

    private String planname;

    private String cleaningtime;

    private String scoperange;

    private String cycle;

    private String standards;

    private String products;

    private String remarks;

    private String dictionary;

    private String plantype;

    private String scoring;

    private String inspectors;

    private Integer company;

    private String delFlag;


}