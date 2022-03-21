package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StCabinet {
    private Integer id;
    private String number;
    private String name;
    private String specification;
    private String addr;
    private String state;

    private String personnel;
    private String phone;
    private String pickTime;

    private String orderNo;
    private String mode;
    private String details;
    private String launchTime;
    private String shelf;
    private Integer tablewareNum;

}
