package com.atguigu.cache.mapper;

import com.atguigu.cache.bean.Product;

public interface ProductMapper {

    Long get(Long ProductId);

    Product update(Product product);

    Product Create(Product product);


}
