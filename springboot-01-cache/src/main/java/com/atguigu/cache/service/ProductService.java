package com.atguigu.cache.service;

import com.alibaba.fastjson.JSON;
import com.atguigu.cache.Util.RedisKeyPrefixConst;
import com.atguigu.cache.Util.RedisUtil;
import com.atguigu.cache.Util.RedisUtils;
import com.atguigu.cache.bean.Product;
import com.atguigu.cache.mapper.ProductMapper;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;

public class ProductService {


    public static final Integer PRODUCT_CACHE_TIMEOUT = 60 * 60 * 24;
    public static final String EMPTY_CACHE = "{}";
    public static final String LOCK_PRODUCT_HOT_CACHE_CREATE_PREFIX = "lock:product:hot_cache_creat:";
    public static final String LOCK_PRODUCT_CACHE_UPDATE_PREFIX = "lock:product:update:";


    @Autowired
    private RedisUtils redisUtil;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    Redisson redisson;

    /**
     * 创建商品将商品添加到缓存
     */
    @Transactional
    public Product create(Product product) {
        //dao调用添加数据库
        Product productResult = productMapper.Create(product);
        redisUtil.set(RedisKeyPrefixConst.PRODUCT_CACHE + productResult.getId(), JSON.toJSONString(productResult), getProductCacheTimeout(), TimeUnit.SECONDS);
        //添加到缓存
        //    redisUtil.get();
        return productResult;
    }

    /**
     * 修改商品将商品添加到缓存
     */
    @Transactional
    public Product update(Product product) {

        Product productResult = null;

        //解决双写不一致的问题：在修改数据库加缓存的时候加分布式锁  在写的时候不允许别的线程去写或者去读让其串行执行   即两个线程针对同一个商品id才会出现锁互斥现象
        /*RLock productUpdateLock = redisson.getLock(LOCK_PRODUCT_CACHE_UPDATE_PREFIX + product.getId());
        productUpdateLock.lock();*/
//        优化加读写锁
        RReadWriteLock rReadWriteLock = redisson.getReadWriteLock(LOCK_PRODUCT_CACHE_UPDATE_PREFIX + product.getId());
        RLock rLock = rReadWriteLock.readLock();
        rLock.lock(); //加写锁
        try {
            //修改数据库
            productResult = productMapper.update(product);
            //添加到缓存
            redisUtil.set(RedisKeyPrefixConst.PRODUCT_CACHE + productResult.getId(), JSON.toJSONString(productResult), getProductCacheTimeout(), TimeUnit.SECONDS);

        } finally {
            rLock.unlock();
        }
        return productResult;
    }


    /**
     * 获取商品信息
     */
    @Transactional
    public Product get(Long productId) {
        Product product = null;
        String productCanchekey = RedisKeyPrefixConst.PRODUCT_CACHE + productId;
        product = getProductFromCache(productCanchekey);

        if (product != null) {
            return product;
        }


        //双层检测锁：两次查询缓存      冷数据突然来了之后：排队一个一个的进    解决并发重建问题
        //自己的商品可以等着，其他的商品就会等待   分布式锁
        RLock hotCacheCreateLock = redisson.getLock(LOCK_PRODUCT_HOT_CACHE_CREATE_PREFIX + productId);
        hotCacheCreateLock.lock();//setnx(k,v)
//        优化：
        /*try {
            hotCacheCreateLock.tryLock(1,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        try {
            product = getProductFromCache(productCanchekey);
            if (product != null) {
                return product;
            }
            //解决双写不一致的问题：在查询数据库之前加分布式锁
            /*RLock productUpdateLock = redisson.getLock(LOCK_PRODUCT_CACHE_UPDATE_PREFIX + productId);
            productUpdateLock.lock();*/
//            优化加读写锁
            ReadWriteLock readWriteLock = redisson.getReadWriteLock(LOCK_PRODUCT_CACHE_UPDATE_PREFIX + productId);
            RLock rlock = (RLock) readWriteLock.readLock();
            rlock.lock();
            try {
                //几十万同事走数据库会导致数据库挂掉    即突发性的热点缓存
                productMapper.get(productId);
                if (product != null) {
                    //设置缓存时一般给个超时时间  一天没有访问的数据让其缓存失效   经常访问的在缓存里仍然有效
                    redisUtil.set(productCanchekey, JSON.toJSONString(product), getProductCacheTimeout(), TimeUnit.SECONDS);
                } else {
                    //如果查数据库没有值放一个空字符串   id  一分多种失效
                    redisUtil.set(productCanchekey, EMPTY_CACHE, getEmptyCacheTimeout(), TimeUnit.SECONDS);
                }
            } finally {
                rlock.unlock(); //加读锁
            }
        } finally {
            hotCacheCreateLock.unlock();   //释放锁   删除缓存lockkey  底层del
        }
        return product;
    }


    private Integer getProductCacheTimeout() {
        return PRODUCT_CACHE_TIMEOUT + new Random().nextInt(5) * 60 * 60;
    }


    private Integer getEmptyCacheTimeout() {
        return 60 + new Random().nextInt(30);
    }


    private Product getProductFromCache(String productCanchekey) {
        Product product = null;
        String productStr = (String) redisUtil.get(productCanchekey);
        if (productStr != null && productStr != "") {
            //解决缓存穿透
            if (EMPTY_CACHE.equals(productStr)) {
                //缓存延期     不换id一直用同一个id请求 一直在缓存里面进行延期
//                redisUtil.expire(productCanchekey,getProductCacheTimeout(), TimeUnit.SECONDS);
                redisUtil.set(productCanchekey, EMPTY_CACHE, getEmptyCacheTimeout(), TimeUnit.SECONDS);
                //如果是空缓存，前端判断当前数据不存在
                return new Product();
            }

            JSON.parseObject(productCanchekey, Product.class);
            //读延期     读一天一直在redis查   把缓存做一个延期   只要一天之内经常访问的数据一直在缓存里面
            redisUtil.expire(productCanchekey, getProductCacheTimeout(), TimeUnit.SECONDS);
        }
        return product;
    }


}
