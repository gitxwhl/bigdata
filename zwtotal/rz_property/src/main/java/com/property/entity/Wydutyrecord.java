package com.property.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Wydutyrecord {
    private Integer id;

    private String timestamp;

    private String frequency;

    private String events;

    private String gatekeeper;

    private String company;


}