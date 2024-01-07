package com.atguigu.mybatisplus.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum SexEnum {
    MAIL(1,"男"),

    FEMALE(0,"女")
    ;



    @EnumValue  //将注解所标识的属性的值存到数据库中
    private Integer sex;
    private String sexName;





    SexEnum(Integer sex, String sexName) {
        this.sex = sex;
        this.sexName = sexName;
    }
}
