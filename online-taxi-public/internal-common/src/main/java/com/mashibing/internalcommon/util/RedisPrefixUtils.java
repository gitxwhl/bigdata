package com.mashibing.internalcommon.util;

/**
 * redis  key 生成管理
 */
public class RedisPrefixUtils {

    //       乘客验证码前缀
    private static final String  verificationCodePrefix="passenge-verification-code-";
    //tooken前缀
    private static final String tookenPreFix="tooken-";


    /**
     *生成key
     */
    public static String getkeyByphone(String passengerPhone){
        return verificationCodePrefix + passengerPhone;
    }


    /**
     * tooken存入redis
     * @param passengerPhone
     * @param identiy
     * @return
     */
    public static String getGenraTooken(String passengerPhone,String identiy,String tokenType){
        return tookenPreFix + passengerPhone + "-" + identiy + "-" + tokenType;
    }




}
