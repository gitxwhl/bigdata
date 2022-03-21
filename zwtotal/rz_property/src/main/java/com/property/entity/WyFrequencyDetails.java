package com.property.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WyFrequencyDetails {
    private Integer id;

    private String timestamp;

    private String frequency;

    private String startTime;

    private String endTime;

}
