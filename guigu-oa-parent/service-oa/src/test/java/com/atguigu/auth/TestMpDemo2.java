package com.atguigu.auth;


import atguigu.model.system.SysRole;
import com.atguigu.service.SysRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
public class TestMpDemo2 {

    //注入
    @Autowired
    private SysRoleService service;

    //查询所有记录
    @Test
    public void getAll() {
        List<SysRole> list = service.list();
        System.out.println(list);
    }

}
