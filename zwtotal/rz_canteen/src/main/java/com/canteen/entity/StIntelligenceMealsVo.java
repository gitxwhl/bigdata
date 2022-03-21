package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class StIntelligenceMealsVo {

    private int id;

    private String name;

    private String meals;

    private String dishescategory;

    private Timestamp date;

    //List<StIntelligenceMealsVo> Subclass  = new ArrayList<>();
}
