package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Stmeteringsystemsettings {

    private Integer id;
    /**
     *菜品同步时间（秒）
     */
    private String dishTime;
    /**
     *餐盘检测时间（秒）
     */
    private String platedetectionTime;
    /**
     *剩余预警重量（克）
     */
    private String warningWeight;

    /**
     *同步时间
     */
    private String synchronizationTime;
    /**
     *状态
     */
    private String state;
    /**
     *运维餐厅(外键:详见运维餐厅表)
     */
    private String operationreStaurant;
    /**
     *运维餐厅集合
     */
    //private List<StOperationrestaurant> operationreStaurants;

    //逻辑删除字段
    private String delFlag;

}