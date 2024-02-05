package com.mashibing.easypoi_boot.pojo;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@ExcelTarget("loginusr")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginUsr implements Serializable {
    @Excel(name = "用户id",orderNum = "1")
    private String id;
    @Excel(name = "用户名称",orderNum = "2")
    private String nickname;
    @Excel(name = "密码",orderNum = "3")
    private String password;
    @Excel(name = "注册时间",orderNum = "4",format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @Excel(name = "状态",orderNum = "5",replace = {"VIP_1","普通用户_0"})
    private String status;
}
