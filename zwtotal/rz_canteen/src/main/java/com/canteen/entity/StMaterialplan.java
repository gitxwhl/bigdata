package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StMaterialplan {
    Integer id;
    String ingredientsName;
    String requirement;
    String demandUnit;
    String inventory;
    String inventoryUnit;
    String revisedDemand;
    String revisionUnit;
    String rowMeal;

}
