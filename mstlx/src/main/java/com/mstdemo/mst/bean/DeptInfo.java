package com.mstdemo.mst.bean;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class DeptInfo {

    private Integer deptId;
    @NotBlank(message = "部门不能为空")
//    @Length(min=6,max = 20,message = "部门长度在6到20之间")
//    @Pattern(regexp = "^1[0-9]{10}$",message = "手机号密码错误")
    private String deptName;
//    @Min(value = 0,message="年龄最小为0")
//    @Max(value = 100,message = "年龄最大为100")
    private String sort;


}
