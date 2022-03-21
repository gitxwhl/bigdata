package com.officeServices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class GoodTypeTree {
    /**
     *类型名称
     */
    private String id;
    /**
     *创建日期
     */
    private String typeName;
    /**
     *父节点
     */
    private String pId;


}
