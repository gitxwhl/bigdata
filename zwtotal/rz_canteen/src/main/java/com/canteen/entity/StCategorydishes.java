package com.canteen.entity;

import com.canteen.entity.bo.Parent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class StCategorydishes extends Parent implements Serializable  {

    private Integer id;
    /**
     *所有父级品类
     */
    private Integer parentIds;
    /**
     *品类名称
     */
    private String varietiesName;
    /**
     *品类编码
     */
    private String varietiesCode;
    /**
     *备注
     */
    private String remarks;
    /**
     *排序
     */
    private Long sort;
    /**
     *创建者
     */
    private String createBy;
    /**
     *创建时间
     */
    private Date createDate;
    /**
     *修改者
     */
    private String updateBy;
    /**
     *修改时间
     */
    private Date updateTime;
    /**
     *删除标记  0正常  1删除  2停用
     */
    private String delFlag;
    /**
     *分类级别
     */
    private Integer level;
    /**
     * 喜好餐厅 字段不关联，如果需要关联
     */
    private String affiliatedrestaurant;


    List<StCategorydishes> Subclass  = new ArrayList<>();


}