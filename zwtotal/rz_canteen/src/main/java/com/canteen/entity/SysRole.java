package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;
/**
 * 角色
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class SysRole implements Serializable {
    private String id;

    private String name;

    private String type;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private String delFlag;

    private String remarks;

}