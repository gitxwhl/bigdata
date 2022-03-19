package com.raysdata.enterprisesadmittanceserver.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface PoiFileds {
        //猎头
        String title();
        //属性名字
        String name();
        //属性类型
        int type() default 0;
    }
