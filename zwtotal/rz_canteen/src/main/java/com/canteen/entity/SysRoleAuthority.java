package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@NoArgsConstructor
@Data
@AllArgsConstructor
public class SysRoleAuthority implements Serializable {
    private Integer id;

    private String authorityId;

    private String roleId;

}