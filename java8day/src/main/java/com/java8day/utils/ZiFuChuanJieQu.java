package com.java8day.utils;

public class ZiFuChuanJieQu {

    public static void main(String[] args) {
        String str = "<abcd efg>higklmnopq<rstu vwxyz";
        System.out.println(str.substring(str.indexOf("<")+1,str.lastIndexOf(">")));
    }
}
