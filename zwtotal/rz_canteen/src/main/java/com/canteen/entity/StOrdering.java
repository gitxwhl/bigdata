package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StOrdering {
    private Integer id;
    private String  dictionarie;
    private String  orderNum;
    private String  personnel;
    private String  information;
    private String  card;
    private String  causes;
    private String  menuPlan;
    private String  state;
    private String  addr;
    private String  address;
}
