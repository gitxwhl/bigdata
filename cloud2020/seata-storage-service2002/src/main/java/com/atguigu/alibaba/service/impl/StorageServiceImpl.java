package com.atguigu.alibaba.service.impl;

import com.atguigu.alibaba.dao.StorageDao;
import com.atguigu.alibaba.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private StorageDao StorageDao;

    /**
     * 修改库存
     * @param productId
     * @param count
     */
    @Override
    public void decrease(Integer productId, Integer count) {
        StorageDao.decrease(productId,count);
    }
}
