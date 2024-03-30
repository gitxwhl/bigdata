package com.atguigu.decorator;
//具体的Decorator，这里就是调味品
public class Chocolate extends Decorator{
    public Chocolate(Drink obj) {
        super(obj);
        setDesc("巧克力");
        setPrice(5.0f);//调味品价格
    }
}
