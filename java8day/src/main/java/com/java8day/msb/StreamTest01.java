package com.java8day.msb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StreamTest01 {
    public static void main(String[] args) {
        List<String> ss = Arrays.asList("张", "张三", "张三丰", "李四", "王五");
        ArrayList<String> s1 = new ArrayList<>();
        //获取所有姓张的信息
        for (String s : ss) {
            if (s.startsWith("张")) {
                s1.add(s);
            }
        }
        //获取长度为3的信息
        ArrayList<String> s2 = new ArrayList<>();
        for (String s : s1) {
            if (s.length() == 3) {
                s2.add(s);
            }
        }
        for (String s : s2) {
            System.out.println(s);
        }
        System.out.println("---------------------------------");
        ss.stream().filter(s -> s.startsWith("张"))
                .filter(s -> s.length() == 3).
                forEach(System.out::println);
    }
}
