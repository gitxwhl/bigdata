package com.atguigu.decorator;

public class CoffeeClint {

    public static void main(String[] args) {
        //用装饰者模式下订单，2份巧克力加1份牛奶的LongBlack

        //点一份LongBlack
        Drink order = new LongBlack();
        System.out.println("费用=" + order.getPrice());
        System.out.println("描述=" + order.getDesc());
        //order  加入一份牛奶
         order = new Milk(order);
        System.out.println("order 加入一份牛奶的费用=" + order.cost());
        System.out.println("order 加入一份牛奶的描述=" + order.getDesc());
        //  order 加入一份巧克力
         order = new Chocolate(order);
        System.out.println("order 加入一份牛奶   再加入一份巧克力费用=" + order.cost());
        System.out.println("order 加入一份牛奶    再加入一份巧克力描述=" + order.getDesc());
    }
}
