package com.property.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Wyspace {
    private Integer id;

    private String spacename;

    private String builtarea;

    private String greeningratio;

    private String detailedaddress;

    private String description;

    private String enclosure;

    private String equipmentIds;

    private String delFlag;

    private Integer corporatid;

    private List list;
}