package com.atguigu.cache.service;

import com.alibaba.fastjson.JSON;
import com.atguigu.cache.Util.RedisKeyPrefixConst;
import com.atguigu.cache.Util.RedisUtil;
import com.atguigu.cache.Util.RedisUtils;
import com.atguigu.cache.bean.Product;
import com.atguigu.cache.mapper.ProductMapper;
import org.redisson.Redisson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

public class ProductService{


    public static final Integer PRODUCT_CACHE_TIMEOUT =60*60*24;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    ProductMapper productMapper;

    Redisson redisson;

    /**
     * 创建商品将商品添加到缓存
     */
    @Transactional
    public Product create(Product product){
        //dao调用添加数据库
        Product productResult = productMapper.Create(product);
        redisUtil.set(RedisKeyPrefixConst.PRODUCT_CACHE + productResult.getId(),JSON.toJSONString(productResult));
        //添加到缓存
    //    redisUtil.get();
        return productResult;
    }
    /**
     * 修改商品将商品添加到缓存
     */
    @Transactional
    public Product update(Product product){
        //修改数据库
        Product productResult= productMapper.update(product);
        //添加到缓存
        redisUtil.set(RedisKeyPrefixConst.PRODUCT_CACHE + productResult.getId(),JSON.toJSONString(productResult));
        return productResult;
    }



    /**
     * 获取商品信息
     */
    @Transactional
    public Product get(Long productId){
        Product product=null;
        String productCanchekey = RedisKeyPrefixConst.PRODUCT_CACHE + productId;
        String productStr=  redisUtil.get(productCanchekey);
        if(productStr !=null && productStr !=""){
          JSON.parseObject(productCanchekey,Product.class);
        }
        productMapper.get(productId);
        if(product !=  null){
            redisUtil.set(productCanchekey,JSON.toJSONString(product));
        }
        return product;
    }

    private Integer getProductCacheTimeout(){
        return  PRODUCT_CACHE_TIMEOUT+ new Random().nextInt(5)*60*60;
    }







}
