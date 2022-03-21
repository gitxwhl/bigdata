package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
@Data
@AllArgsConstructor
public class SysMenu {
    private String id;

    private String parentId;

    private String memuname;

    private Integer sort;

    private String href;

    private String isShow;

    private String permission;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    private String remarks;

    private String delFlag;

    private String menuType;
}