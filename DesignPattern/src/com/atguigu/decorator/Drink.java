package com.atguigu.decorator;

public abstract class Drink {

    public String desc;

    private Float price= 0.0f;


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
    //让计算费用的抽象方法子类来实现
    public abstract float cost();


}
