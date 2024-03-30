package com.atguigu.decorator;


public  class Decorator extends Drink{

    private Drink obj;

    public Decorator(Drink obj){//组合
        this.obj=obj;

    }
    @Override
    public float cost() {
        //getPrice自己的价格
        return super.getPrice() +obj.cost();
    }

    @Override
    public String getDesc() {
        //obj.getDesc()   输出被装饰者的信息
        return super.desc+super.getPrice()+"&&" + obj.getDesc();
    }





}
