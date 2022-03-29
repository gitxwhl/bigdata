package com.atguigu.alibaba.service;

import org.apache.ibatis.annotations.Param;

public interface StorageService {
    void decrease(@Param("productId")Integer productId, @Param("count")Integer count);
}
