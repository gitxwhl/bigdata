package com.java8day.pojo;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestLambdaLianXi {



    //Lambda表达式的定制排序

    List<Employee> emps= Arrays.asList(
            new Employee(101,"张三",18,999.9),
            new Employee(101,"李四",59,666.6),
            new Employee(101,"王五",28,333.33),
            new Employee(101,"赵六",8,777.7),
            new Employee(101,"田七",38,555.5)
    );


    @Test
    public void test1(){
        Collections.sort(emps,(e1,e2)-> {
            if(e1.getAge()==e2.getAge()){
                return e1.getName().compareTo(e2.getName());
            }else {
                return Integer.compare(e1.getAge(),e2.getAge());
            }
        });

        for(Employee emp :emps){
            System.out.println(emp);
        }
    }

    @Test
    public void test2(){
        //利用lamdaga 表达式进行去除空格

        String ab= getval("\t\t\t 卡发动机咖啡机",(x)->x.trim());
        System.out.println(ab);


    }

    //处理字符串
    public String getval(String st,MyFuntion myf){
        return myf.getvalue(st);
    }

    @Test
    public void test3(){
        //利用lamdaga 表达式lang进行处理
        op(10L,20L,(x,y) ->x+y);
    }

    //两个lang 数据进行处理
    public void op(Long a1,Long a2,MyFunction2<Long,Long> mf){
        System.out.println(mf.getValue(a1,a2));
    }

















}
