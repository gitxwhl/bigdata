package com.atguigu.factory.pizza;

public class GreePizza extends Pizza{

    @Override
    public void prepare() {
        System.out.println("给希腊披萨，准备原材料");
    }
}
