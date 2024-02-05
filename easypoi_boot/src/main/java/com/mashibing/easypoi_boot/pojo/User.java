package com.mashibing.easypoi_boot.pojo;
import cn.afterturn.easypoi.excel.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Data
@ExcelTarget("users")//标注当前类是用于Excel导入导出
public class User implements Serializable {
    @Excel(name="编号",orderNum = "1")
    private String id;
    @Excel(name="姓名",orderNum = "2")
    private String name;
    @Excel(name="年龄",orderNum = "4",suffix = "$")
    private Integer age;
    @Excel(name="生日",orderNum = "3",exportFormat = "yyyy年MM月dd日",width = 20.0)
    private Date bir;
    @Excel(name = "状态",orderNum = "6",replace = {"未激活_0","激活_1"})
    private String status;//0未激活，1激活   数字到文字的替换
    @Excel(name = "爱好",orderNum = "7")
    private List<String> hobby;
    //一对一关系
//    @ExcelIgnore //排除注解
    @ExcelEntity(name = "身份信息")
    private Card card;
    //一对多关系
//    @ExcelIgnore //排除注解
//    @ExcelCollection(name = "订单",orderNum = "8")
//    private List<Order> orderList;
    //图片
    @Excel(name = "头像信息",type = 2,orderNum = "0",width = 12,height = 12)
    private String photo;
}
