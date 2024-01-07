package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.mapper.ProductMapper;
import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.Product;
import com.atguigu.mybatisplus.pojo.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MybatisPlusPluginsTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProductMapper productMapper;

    /**
     * 分页
     */
    @Test
    public void testpage(){
        Page<User> page =new Page<>(2,2);
         userMapper.selectPage(page,null);
        System.out.println(page);
        System.out.println("获取当前页数据recouds"+ page.getRecords()
                +"获取当前页页码 current" + page.getCurrent() + "获取每页显示的条数size"+ page.getSize()
                +"获取总页数page"+page.getPages() + "获取总记录数total"+
                page.getTotal()+"有没有下一页newxt"+page.hasNext()+"判断是否有上一页previous" + page.hasPrevious()

        );



    }
    /**
     * 自定义分页
     */
    @Test
    public void testpage1(){
        Page<User> page=new Page<>(1,2);
        Page<User> pageVo = userMapper.pageVo(page,10);
        System.out.println(
                "获取当前页数据recouds"+ pageVo.getRecords()
                        +"获取当前页页码 current" + pageVo.getCurrent() + "获取每页显示的条数size"+ pageVo.getSize()
                        +"获取总页数page"+pageVo.getPages() + "获取总记录数total"+
                        pageVo.getTotal()+"有没有下一页newxt"+pageVo.hasNext()+"判断是否有上一页previous" + pageVo.hasPrevious()
        );
    }
    /**
     * 乐观锁
     */
    @Test
    public void testLock(){
       //查询小李的商品价格
        Product productLi= productMapper.selectById(1);
        //查询小王的商品价格
        Product productWa=productMapper.selectById(1);
        //小李+50
        productLi.setPrice(productLi.getPrice()+50);
        productMapper.updateById(productLi);
        System.out.println("小李查询商品价格+"+ productLi.getPrice());

        //小王减30
        productWa.setPrice(productWa.getPrice()-30);
        int result = productMapper.updateById(productWa);
        System.out.println("小王查询商品价格 + " +productWa.getPrice());
        if(result==0){
            Product productnew=productMapper.selectById(1);
            productnew.setPrice(productnew.getPrice()-30);
            productMapper.updateById(productnew);
        }


        //老板查询商品价格
        Product productLb = productMapper.selectById(1);
        System.out.println("老板查询价格" + productLb.getPrice());






    }


}
