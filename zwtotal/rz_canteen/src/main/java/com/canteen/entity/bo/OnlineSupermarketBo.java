package com.canteen.entity.bo;

import com.canteen.entity.StMydishes;
import com.canteen.entity.StOnlineshop;
import lombok.Data;

/**
 * @Description: 网上超市查询对象 缺少库存
 */
@Data
public class OnlineSupermarketBo   extends StMydishes {

    /**
     * 可购买的商品数量
     */
    private  int  foodNumber ;
    private  String   categoryNames;




}
