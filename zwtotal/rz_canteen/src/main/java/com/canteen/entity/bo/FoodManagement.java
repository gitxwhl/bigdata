package com.canteen.entity.bo;

import com.canteen.entity.StfoodManagement;
import com.canteen.entity.StnutritionalComponents;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodManagement {

    private StfoodManagement stfoodManagement;

    private StnutritionalComponents components;

    private List<Map> param;
}
