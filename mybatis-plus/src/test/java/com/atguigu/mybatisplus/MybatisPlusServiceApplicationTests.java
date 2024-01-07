package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.pojo.User;
import com.atguigu.mybatisplus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MybatisPlusServiceApplicationTests {
    @Autowired
    private UserService userService;
    @Test
    public void TestCount(){
        //查询总记录数
        Long count = userService.count();
        System.out.println("查询总记录数："+count);
    }

    /**
     * 批量添加
     */
    @Test
    public void testMore(){
//        批量添加，单个的sql进行循环添加   INSERT INTO user ( id, name, age ) VALUES ( ?, ?, ? )
        List<User> list = new ArrayList<>();
        for (int i=0;i <=10;i++){
            User user=new User();
            user.setName("张三"+ i);
            user.setAge(10 + i);
            list.add(user);

        }
        boolean result =userService.saveBatch(list);
        System.out.println(result);

    }


}
