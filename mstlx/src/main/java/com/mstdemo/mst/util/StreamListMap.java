package com.mstdemo.mst.util;

import com.mstdemo.mst.bean.Student;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamListMap {

    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();
        list.add(new Student("1","小明"));
        list.add(new Student("1","小天"));
        list.add(new Student("1","小五"));
        list.add(new Student("2","小红"));
        /**
         * 兰姆达表达式stream流.list转换成map   方式一：
         * @param args
         */
//        Map<String,Student> collect  = list.stream().collect(Collectors.toMap(Student::getId, e->e,(k1, k2)->k2));
//        System.out.println(collect);
        /**
         * 兰姆达表达式list转换成map   方式二：
         * 这种方式如果map出现重复的key值的话，需要加(a,b)->b，否则报错
         * @param args
         */
        Map<String,Student> collect1  = list.stream().collect(Collectors.toMap(Student::getSid, Function.identity(),(a,b)->b));
        System.out.println(collect1);

        /**
         * 兰姆达表达stream流，lmap转换成list
         * @param args
         */
          /* Map<Integer,String> mapList = new HashMap<>();
            mapList.put(1008,"cc");
            mapList.put(1009,"aa");
            mapList.put(1011,"bb");
            mapList.put(1010,"dd");
            mapList.put(1020,"ee");
            List<Integer> list1 = mapList.keySet().stream().collect(Collectors.toList());
            System.out.println(list1);*/



    }




}
