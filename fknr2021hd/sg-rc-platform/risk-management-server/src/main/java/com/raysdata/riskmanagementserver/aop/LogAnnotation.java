package com.raysdata.riskmanagementserver.aop;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

     String module() default "";
     String operator() default "";
     String xtcs() default "";
     String AllXtcs() default "";

}
