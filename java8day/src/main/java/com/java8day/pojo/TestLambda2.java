package com.java8day.pojo;

import org.junit.Test;

import java.sql.SQLOutput;
import java.util.Comparator;
import java.util.function.Consumer;

/**
 *
 *lambda
 * 箭头左侧：表达式参数列表
 * 箭头右侧：表达式所需执行的功能 即lambda体
 *
 * 语法格式 1：无参数，无返回值
 *   （）-> System.out.println("hello")
 *语法格式2：有一个参数，并且无返回值
 * (x) -> System.out.println(x);
 * 语法格式3：若只有一个参数，小括号可以省略不写
 * x -> System.out.println(x);
 *语法格式四：有两个以上的参数,有返回值，并且lambda 体中有多条语句
 * Comparator<Integer> con = (x, y) -> {
 *             System.out.println("两个参数");
 *             return Integer.compare(x,y);
 *         };
 *语法格式五：
 * 有两个参数，有返回值并且 lambda 体中只有一条语句   大括号  和retun可以省略
 *  Comparator<Integer> con  =  (x,y) -> Integer.compare(x,y);
 *
 * 语法格式六：Lambda 表达式的参数类型可以省略不写，因为JVM编译器可以推断上下文 数据类型  即类型推断
 *
 * Comparator<Integer> con  =   (Intesger x,Intesger y) -> Integer.compare(x,y);
 *左右遇一括号省
 * 左侧推断类型省
 *
 * 二：Lambda 表达是需要函数式接口的支持
 * 函数式接口：接口中只有一个抽象方法的接口，称为函数式接口 可以用   @FunctionalInterface
 * 可以检测是否是函数式接口
 */
public class TestLambda2 {


    @Test
    public void test1(){
        int num=0;  //jdk 1.7 前必须final


        //匿名内部类
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("无参");
            }
        };
        r.run();
        System.out.println("---------------------------->");
        Runnable r1 = () -> System.out.println("无参数，无返回值");
                 r1.run();
    }

    @Test
    public void test2(){
            Consumer<String> con = (x) -> System.out.println(x);
            con.accept("有一个参数，并且无返回值");
    }

    @Test
    public void test3(){
        Comparator<Integer> con = (x, y) -> {
            System.out.println("两个参数");
            return Integer.compare(x,y);
        };
    }

    @Test
    public void  test4(){
       Comparator<Integer> con  =  (x,y) -> Integer.compare(x,y);

    }


    //对一个数进行运算

    @Test
    public void  test5(){

       Integer nu = operation(100,(x)->x*x);
        System.out.println(nu);

        System.out.println(operation(200,(y)->y+200));

    }



    public Integer operation(Integer num ,MyFun myFun){

       return myFun.getValue(num);
    }





}
