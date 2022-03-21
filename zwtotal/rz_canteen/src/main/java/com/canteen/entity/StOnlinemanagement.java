package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StOnlinemanagement {
    private Integer id;
    private String timestamp;
    private String isReserve;
    private String isDistribution;
}
