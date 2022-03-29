package com.atguigu.alibaba.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StorageDao {
    /**
     * 修改库存
     */
    void decrease(@Param("productId")Integer productId,@Param("count")Integer count);


}
