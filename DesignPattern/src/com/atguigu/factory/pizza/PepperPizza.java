package com.atguigu.factory.pizza;

public class PepperPizza extends Pizza{
    @Override
    public void prepare() {
        System.out.println("给胡椒披萨，准备原材料");
    }
}
