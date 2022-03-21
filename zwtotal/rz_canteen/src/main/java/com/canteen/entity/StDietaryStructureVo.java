package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class StDietaryStructureVo {//
    private String dishCode;

    private String cereal;

    private String vegetable;

    private String fishmeat;

    private String milk;

    private String oilsalt;
}
