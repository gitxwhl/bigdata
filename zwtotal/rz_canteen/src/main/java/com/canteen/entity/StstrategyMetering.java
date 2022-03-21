package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class StstrategyMetering {
    private Integer id;

    private String policyname;

    private String policycode;

    private String restaurant;

    private String meteringequipment;

    private String applicabletime;

    private String settlementamount;

    private String remarks;

    private String delFlag;
}