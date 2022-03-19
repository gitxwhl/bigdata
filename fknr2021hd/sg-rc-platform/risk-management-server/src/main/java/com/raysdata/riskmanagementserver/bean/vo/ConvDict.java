package com.raysdata.riskmanagementserver.bean.vo;

import java.lang.annotation.*;

/**
 * * Copyright ©2003- 2020 State Grid Corporation of China,All Rights Reserved.
 * Copyright: NARI
 *
 * @Project: 安全生产风险管控平台
 * @Version：V1.0
 * @Package: com.nariit.rmcp.product.controller.work
 * @Author: chenchao
 * @Create: 2020/9/27 10:49
 * @Description: 转换字典注解
 * @History: modify
 **/
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConvDict {

    /**
     * 类型 用于判断是在类变量上还是属性变量上，默认属性
     * @return
     */
    String type() default Const.DICT_DATATYPE_FIELD;

    /**
     * 映射转换的字段
     * @return
     */
    String aliasField() default "";

    /**
     * 分割符(例如专业现在可以有多个,使用“,”进行分割)
     * @return
     */
    String cutFlag() default "";
}
