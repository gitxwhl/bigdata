package com.canteen.service.impl;

import com.canteen.entity.StShopOrder;
import com.canteen.mapper.StCommonMapper;
import com.canteen.mapper.StShopOrderMapper;
import com.canteen.service.IShopOrderService;
import com.canteen.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: lilong
 * @Date: 2020/11/26 17:19
 * @Description:
 */
@Transactional(rollbackFor = { Exception.class })
@Service
public class ShopOrderServiceImpl implements IShopOrderService {

    @Autowired
    private StShopOrderMapper mapper;


    @Override
    public Map<String,Object> getshopOrderList(StShopOrder entity) {
        Map<String,Object> map = new HashMap<>();
        Integer startNum = ( entity.getPageNum() -1)* entity.getPageSize();
        entity.setPageNum(startNum);

        List<StShopOrder> list = mapper.getshopOrderList(entity);
        map.put("result",list);
        int total = mapper.getshopOrderCount(entity);
        map.put("count",total);
        return map;
    }

    @Override
    public Map<String, Object> getOrderDetail(String orderId) {
        return mapper.getOrderDetail(orderId);
    }


}