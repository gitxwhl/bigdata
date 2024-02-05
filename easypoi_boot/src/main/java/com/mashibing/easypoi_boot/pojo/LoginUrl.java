package com.mashibing.easypoi_boot.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ExcelTarget("loginUrl")
@NoArgsConstructor
@AllArgsConstructor
public class LoginUrl {
    @Excel(name = "用户id",orderNum = "1")
    private String userId;
    @Excel(name = "请求类型",orderNum = "2")
    private String type;
    @Excel(name = "访问地址",orderNum = "3")
    private String url;

}
