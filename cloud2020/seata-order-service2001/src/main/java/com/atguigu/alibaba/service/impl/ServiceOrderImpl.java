package com.atguigu.alibaba.service.impl;

import com.atguigu.alibaba.dao.OrderDao;
import com.atguigu.alibaba.domain.Order;
import com.atguigu.alibaba.service.AccountService;
import com.atguigu.alibaba.service.OrderService;
import com.atguigu.alibaba.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ServiceOrderImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private StorageService storageService;
    @Autowired
    private AccountService accountService;

    /**
     * 创建订单-》调用库存服务扣减库存-》调用账户服务扣减账户余额-》修改订单状态
     * rollbackFor:发生的任何异常通通回滚
     * @param order
     * @return
     */
    @Override
    @GlobalTransactional(name = "fsp-create-order",rollbackFor =Exception.class)
    public void create(Order order) {
        log.info("----------------------下订单");
        orderDao.create(order);
        log.info("----------------------减库存扣减数量");
        storageService.decrease(order.getProductId(),order.getCount());
        log.info("----------------------扣余额");
        accountService.decrease(order.getUserId(),order.getMoney());
        log.info("----------------------修改订单状态");
        orderDao.upedate(order.getUserId(),0);
        log.info("----------------------下订单结束");
    }
}
