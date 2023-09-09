package com.atguigu.factory.order;

import com.atguigu.factory.pizza.ChessPizza;
import com.atguigu.factory.pizza.GreePizza;
import com.atguigu.factory.pizza.PepperPizza;
import com.atguigu.factory.pizza.Pizza;

//简单工厂：给订单提供披萨对象
public class SimpleFortory {
    //增加披萨种类返回对应披萨对象
    public Pizza creaPizza(String pizaaType){
        System.out.println("使用简单工厂订购");
        Pizza pizza=null;
            if(pizaaType.equals("greek")){
                pizza=new GreePizza();
                pizza.setName("希腊披萨");
            }else if(pizaaType.equals("chess")){
                pizza.setName("奶酪披萨");
                pizza= new ChessPizza();
            }else if(pizaaType.equals("pepper")){
                pizza =new PepperPizza();
                pizza.setName("胡椒披萨");
            }
        return pizza;
    }


    //方法二：使用静态方法模式
    public static Pizza creaPizza2(String pizaaType){
        System.out.println("使用简单工厂订购2");
        Pizza pizza=null;
        if(pizaaType.equals("greek")){
            pizza=new GreePizza();
            pizza.setName("希腊披萨");
        }else if(pizaaType.equals("chess")){
            pizza.setName("奶酪披萨");
            pizza= new ChessPizza();
        }else if(pizaaType.equals("pepper")){
            pizza =new PepperPizza();
            pizza.setName("胡椒披萨");
        }
        return pizza;
    }
}
