package com.mstdemo.mst.util;

public class testTryCatch {
    public static void main(String[] args) {
            get();
    }



    public static void get(){
        try {
            return ;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("finally ");
        }
    }



}
