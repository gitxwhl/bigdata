package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 健康指标
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stbodyproject {

    private String tid;
    /**
     *指标名称
     */
    private String indexName;
    /**
     *指标状态（0：正常 1：不正常）
     */
    private String state;
    /**
     *人员外键
     */
    private String stpersonnel;




}
