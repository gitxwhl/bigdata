package com.canteen.entity.vo;

import lombok.Data;

@Data
public class StGoodcommodityVo {

    /**
     *商品名称
     */
    private String name;
    /**
     *商品类别
     */
    private String typeid;
    /**
     *商品价格
     */
    private String price;
    /**
     *保质期
     */
    private String shelfLife;
    /**
     *库存量
     */
    private String inventory;

}
