package com.raysdata.riskmanagementserver.bean.vo;

import java.lang.annotation.*;

/**
 * * Copyright ©2003- 2020   State Grid Corporation of China, All Rights Reserved.
 * Copyright: NARI
 *
 * @Project: 安全生产风险管控平台
 * @Version：V1.0
 * @Package: com.rmcp.base.service.annotation
 * @Author: zhouyanbin
 * @Create: 4/26/20 11:10 PM
 * @Description: 记录变更注解 该注解只处理映射数据库字段 不允许挂在对象属性上
 * @History: modify
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ChangeLog {

	/**
	 * 字段名称
	 * @return fieldName
	 */
	String fieldName() default "";

	/**
	 * 是否非空
	 * @return boolean
	 */
	boolean validator() default false;

	/**
	 * 别名
	 * @return
	 */
	String alias() default "";

	/**
	 * 是否是字典
	*/
	boolean isDict() default false;

}
