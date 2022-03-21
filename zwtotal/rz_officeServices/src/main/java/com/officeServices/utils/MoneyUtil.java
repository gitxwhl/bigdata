package com.officeServices.utils;

public class MoneyUtil {
    
    public static String moneyAdd(String price1,String price2){
        Double price = Double.valueOf(price1) + Double.valueOf(price2);
        return price.toString();
    }

    public static String moneySub(String price1,String price2){
        Double price = Double.valueOf(price1) - Double.valueOf(price2);
        return price.toString();
    }
}
