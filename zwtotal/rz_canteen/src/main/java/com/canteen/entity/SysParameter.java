package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class SysParameter {
    private Integer id;

    private String paratype;

    private String paraname;

    private String parakey;

    private String paravalue;

    private String paraorder;


}