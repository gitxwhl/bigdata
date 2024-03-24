package com.mashibing.internalcommon.util;

import java.math.BigDecimal;

public class BigDecimalUtil {

    /**
     * 加法
     * @param v1
     * @param v2
     * @return
     */
    public static double add(double v1,double v2){
        BigDecimal b1=new BigDecimal(v1);
        BigDecimal b2= new BigDecimal(v2);
        return b1.add(b2).doubleValue();
    }

    /**
     * 除法
     */
    public static double divide(double v1,double v2){
        if(v2<=0){
           throw new IllegalArgumentException("除数不能为0");
        }
        BigDecimal b1=new BigDecimal(v1);
        BigDecimal b2= new BigDecimal(v2);
        return b1.divide(b2,2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 减法
     * @param v1
     * @param v2
     * @return
     */
    public static double subtract(double v1,double v2){
        BigDecimal b1=new BigDecimal(v1);
        BigDecimal b2=new BigDecimal(v2);
        return b1.subtract(b2).doubleValue();
    }
    /**
     * 乘法
     */
    public static double multiply(double v1,double v2){
        BigDecimal b1=new BigDecimal(v1);
        BigDecimal b2=new BigDecimal(v2);
        return b1.multiply(b2).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }


}
