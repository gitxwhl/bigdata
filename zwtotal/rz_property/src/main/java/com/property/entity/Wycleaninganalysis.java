package com.property.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Wycleaninganalysis {
    private Integer id;

    private String cleaning;

    private String iscompleted;

    private String completiontime;


}