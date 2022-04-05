package com.java8day.pojo;

import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * java8内置的四大核心接口
 * Consumer<T> 消费型接口
 * void accept(T t);
 * Supplier<T> 供给型接口
 * T get();
 * Function<T,R> 函数型接口
 * R apply(T t);
 * Predicate(T) : 断言型接口
 *   boolean test(T t);
 *
 *
 *
 *
 *
 *
 *
 */
public class TestLambda3 {



//    Consumer<T> 消费型接口
    @Test
    public void test1(){
        happy(10000 , (x)-> System.out.println("消费" +x));
    }
    public void happy(double money, Consumer<Double> con){
        con.accept(money);
    }

    //    供给型接口
    @Test
    public void test2(){

        System.out.println("---->"+(int)Math.random()*100);
       List<Integer> numList = getNumList(10,()->(int)(Math.random()*100));
        for (Integer o :numList){
            System.out.println(o);
        }
    }
    //    产生指定个数的整数放入集合中 Supplier<T>
    public List<Integer> getNumList(int num, Supplier<Integer> sup){
        List<Integer> list =new ArrayList<>();
        for (int i=0;i <num;i++){
            Integer n= sup.get();
            list.add(n);
        }
        return list;
    }


    // 函数式接口
    @Test
    public void test3(){

        String aa= strHandle("stasdfasdf",(x) -> x.toUpperCase());
        System.out.println(aa);
    }

    //函数式接口 Function<T,R> 函数型接口
    public String strHandle(String st, Function<String,String> fu){
            return fu.apply(st);
    }


    //断言型接口

    @Test
    public void test4(){
        List<String> ls= Arrays.asList("helllo","asfdf","aaa","s","www","ok");
        List<String> lis = filterStr(ls,(x) -> x.length() > 3);
        for (String o:lis){
            System.out.println(o);
        }
    }

//    将满足条件的字符串放入集合中
public List<String> filterStr(List<String> list, Predicate<String> pre){
    List<String> strlist = new ArrayList<>();
    for (String st:list){
        //满足x.length() > 3 添加集合
        if(pre.test(st)){
            strlist.add(st);
        }
    }
        return strlist;
}



}
