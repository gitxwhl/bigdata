package com.atguigu.factory.order;
//相当于一个客户端订购pizza
public class PizzaStroe {

    public static void main(String[] args) {
        //没有用简单工厂对象调用
        //new OrderPizza();
        //使用简单工厂对象调用
//       new OrderPizza(new SimpleFortory());

        //使用简单工厂2静态方法对象调用
        new OrderPizza2();
       System.out.println("退出了程序~~");

    }
}
