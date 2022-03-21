package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StPenaltyList {
    private int id;
    private String fine;
    private String otherPenaltie;
    private String deductionPoints;
    private String issued;
    private String releaseTime;
    private String reason;
    private String enclosure;
    private int supplierId;
}
