package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StLocaleat {
    private int id;
    private String applicant;
    private String beginTime;
    private String endTime;
    private String place;
    private String restaurant;
    private String remarks;
    private String explainInfo;
    private String approvedBy;
    private String state;
    private String delFlag;
}
