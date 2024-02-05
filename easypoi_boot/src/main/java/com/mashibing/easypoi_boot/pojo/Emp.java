package com.mashibing.easypoi_boot.pojo;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
@ExcelTarget("emps")
public class Emp implements Serializable {
    @Excel(name="编号")
    private String id;
    @Excel(name="姓名")
    private String name;
    @Excel(name="年龄")
    private Integer age;
    @Excel(name="生日",format = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;
    @Excel(name = "状态",replace = {"未激活_0","激活_1"})
    private String status;//0未激活，1激活   数字到文字的替换
//    @Excel(name = "头像",type = 2,savePath = "D:\\github\\bigdata\\easypoi_boot\\src\\main\\resources\\static")
    @Excel(name = "头像",type = 2)
    private String photo;
}
