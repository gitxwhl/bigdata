package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MybatisPlusApplicationTests {
    @Autowired
    private UserMapper userMapper;

    /**
     *通过条件构造器查询一个list集合，若没有条件，则可以设置为null
     *
     */
    @Test
    void contextLoads() {
      QueryWrapper<User> queryWrapper = new QueryWrapper<>();
      List<User> uselist = userMapper.selectList(queryWrapper);
        uselist.forEach(System.out::println);
    }


    /**
     * 测试新增的功能
     */
    @Test
    public void testInsert(){
        User user = new User();
        user.setAge(12L);
        user.setName("张三");
        user.setEmial("zhangsan@qq.com");
        userMapper.insert(user);
        //自动返回主键：mybatis-plus主键是雪花算法生成
        System.out.println("id:====" + user.getUid());

    }
    /**
     * 通过id删除用户数据
     */
    @Test
    public void testDelete(){
        //通过id删除用户数据
//        int result=userMapper.deleteById(0);
//        System.out.println("result===:"+result);
        //根据map条件进行删除
        /*Map<String,Object> map   = new HashMap<>();
        map.put("name","Jone");
        map.put("age","18");
        userMapper.deleteByMap(map);*/
        //通过多个id批量删除(in) DELETE FROM user WHERE id IN ( ? , ? )
        List<Long> list = Arrays.asList(4l,3l);
        int result = userMapper.deleteBatchIds(list);
        System.out.println("result:" +result);
    }
    /**
     * 修改
     */
    @Test
    public void testUpdate(){
        User user = new User();
        user.setUid(2L);
        user.setName("zpp");
        user.setEmial("zpp@qq.com");
        int result = userMapper.updateById(user);
        System.out.println("resutl:" + result);
    }
    /**
     * 查询用户信息
     */
    @Test
    public void testSelect(){
         //根据id查询用户信息
        User result = userMapper.selectById(2);
        System.out.println("result:" + result);
        //根据多个id查多个用户信息  SELECT id,name,age,emial FROM user WHERE id IN ( ? , ? )
        /*List<Long> list = Arrays.asList(2l,5l);
        List<User> resultList = userMapper.selectBatchIds(list);
        resultList.forEach(System.out::println);*/
        //根据map集合条件查询
        /*Map<String,Object> map = new HashMap<>();
        map.put("name","zpp");
        map.put("age",0);
       List<User>  listuser = userMapper.selectByMap(map);
       listuser.forEach(System.out::println);*/
        //自己mybatis实现查询
//        Map<String,Object> resultMap = userMapper.selectMapById(2l);
//        System.out.println( "resultMap:"+ resultMap);





    }
}
