package com.canteen.mapper;


import com.canteen.entity.StShopOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface StShopOrderMapper {
    List<StShopOrder> getshopOrderList(@Param("entity") StShopOrder entity);

    int getshopOrderCount(@Param("entity") StShopOrder entity);

    Map<String,Object> getOrderDetail(@Param("orderId") String orderId);
}
