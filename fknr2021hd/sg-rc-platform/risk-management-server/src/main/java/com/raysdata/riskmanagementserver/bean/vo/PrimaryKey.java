package com.raysdata.riskmanagementserver.bean.vo;

import java.lang.annotation.*;

/**
 * * Copyright ©2003- 2020   State Grid Corporation of China, All Rights Reserved.
 * Copyright: NARI
 *
 * @Project: 安全生产风险管控平台 标记bean类对应数据表的主键字段 和记录对应数据库表
 * @Version：V1.0
 * @Package: com.rmcp.base.service.annotation
 * @Author: zhouyanbin
 * @Create: 4/26/20 11:10 PM
 * @Description: 主键注解 包含表名称
 * @History: modify
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PrimaryKey {

    /**
     * 对应数据库表明
     * @return
     */
    String tableName() default "";

    /**
     * 别名
     * @return
     */
    String alias() default "";
}
