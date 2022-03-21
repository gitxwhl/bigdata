package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Stquantityprice   {

    private Integer id;
    /**
     *菜品名称
     */
    private String dishname;
    /**
     *订餐时间内价格（元）
     */
    private String timepice;
    /**
     *订餐时间外价格（元）
     */
    private String Outtimepice;
    /**
     *员工卡价格（元）
     */
    private String employeecardprice;
    /**
     *临时卡价格（元）
     */
    private String temporarycardPrice;
    /**
     *选餐排菜外键
     */
    private String choosingmeals;
    /**
     *结算策略外键
     */
    private String settlement;
    /**
     *餐厅外键
     */
    private String restaurant;



}