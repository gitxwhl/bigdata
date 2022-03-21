package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
@Data
@AllArgsConstructor
public class SysAuthority {
    private String id;

    private String name;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

}