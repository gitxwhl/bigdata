package com.atguigu.factory.order;

import com.atguigu.factory.pizza.ChessPizza;
import com.atguigu.factory.pizza.GreePizza;
import com.atguigu.factory.pizza.Pizza;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OrderPizza {
//使用普通方式订购披萨
    /*public OrderPizza() {
        Pizza pizza=null;
        String orderType;
        do{
             orderType=getType();
            if(orderType.equals("greek")){
                pizza=new GreePizza();
                pizza.setName("希腊披萨");
            }else if(orderType.equals("chess")){
                pizza.setName("奶酪披萨");
                pizza= new ChessPizza();
            }else {
                break;
            }
            //输出pizza的制作过程
            pizza.prepare();
            //烘烤
            pizza.bake();
            //切割
            pizza.cut();
            //打包
            pizza.box();
        }while (true);

    }*/

    public OrderPizza(SimpleFortory simpleFortory) {
        setFortory(simpleFortory);
    }



//使用简单工厂订购披萨:
//定义一个工厂对象
SimpleFortory simpleFortory;
    //传入工厂对象
public void setFortory(SimpleFortory simpleFortory){
    //获取输入的类型
    String pizzaType;
    //循环输入
    do{
        pizzaType=getType();
        //根据披萨类型调用披萨工厂返回实例披萨
        Pizza pizza = simpleFortory.creaPizza(pizzaType);

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
