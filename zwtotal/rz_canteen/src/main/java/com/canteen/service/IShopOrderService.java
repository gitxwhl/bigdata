package com.canteen.service;

import com.canteen.entity.StShopOrder;
import com.canteen.utils.Result;

import java.util.List;
import java.util.Map;

public interface IShopOrderService {
    Map<String,Object> getshopOrderList(StShopOrder entity);

    Map<String,Object> getOrderDetail(String orderId);
}
