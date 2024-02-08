package com.mashibing.easypoi_boot.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.io.Serializable;

@ExcelTarget("courses")
@Data
public class Course implements Serializable {
    @Excel(name = "课程ID")
    private String cid;
    @Excel(name = "订单编号")
    private String orderno;
    @Excel(name = "课程名称")
    private String cname;
    @Excel(name = "简介")
    private String brief;
    @Excel(name = "价格")
    private double price;



}
