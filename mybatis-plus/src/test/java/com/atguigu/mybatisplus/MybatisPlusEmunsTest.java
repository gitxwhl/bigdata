package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.enums.SexEnum;
import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MybatisPlusEmunsTest {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void test01(){
        User user= new User();
        user.setName("zhang");
        user.setAge(11);
        user.setEmial("zp@qq.com");
        user.setSex(SexEnum.MAIL);
        userMapper.insert(user);
    }

}
