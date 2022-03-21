package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StSupplierScore {
    private int id;
    private String releaseTime;
    private String issued;
    private String timeScore;
    private String scoring;
    private String enclosure;
    private int supplierId;
}
