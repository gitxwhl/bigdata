package com.atguigu.factory.order;

import com.atguigu.factory.pizza.Pizza;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//使用简单工厂静态方法
public class OrderPizza2 {


    public OrderPizza2() {
        //获取输入的类型
        String pizzaType;
        //循环输入
        do{
            pizzaType=getType();
            //根据披萨类型调用披萨工厂返回实例披萨
            Pizza pizza = SimpleFortory.creaPizza2(pizzaType);
            //判断披萨工厂是否含有披萨实例：
            if(pizza !=null){
                //订购披萨成功
                //如果有打印制作过程
                pizza.prepare();
                //烘烤
                pizza.bake();
                //切割
                pizza.cut();
                //打包
                pizza.box();
            }else {
                //如果没有给相应提示
                System.out.println("订购披萨失败");
            }
        }while (true);


    }


//可以获取客户订购的pizza的种类
    private String getType() {
        try {
            BufferedReader strin = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("input pizza 订购披萨的种类:");
            String str = strin.readLine();
            return str;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
