package com.bobo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;
//表示支持jsr250-api的注解支持，需要jsr250-api的jar包
@Controller
@RequestMapping("/user")
public class UserController {
    /**
     * @RolesAllowed  定义接口所需要的权限
     * 每个接口所具有的角色和权限可以放在数据库去管理，在实际开发的时候，接口可以统一设置配置一次性的，因为动态调的是用户的权限，用户的权限
     * 我们要动态去改，固定性的也要去改维护的难度加大了，现在我们只有在接口资源这块写死，后面只要定用户的角色和权限那块做维护就可以了这样
     * 维度就小了
     * @return
     */
    @RolesAllowed(value = {"ROLE_ADMIN"})
    @RequestMapping("/query")
    public String query(){
        System.out.println("用户查询....");
        return "/home.jsp";
    }
    @RolesAllowed(value = {"ROLE_USER"})
    @RequestMapping("/save")
    public String save(){
        System.out.println("用户添加....");
        return "/home.jsp";
    }

    @RequestMapping("/update")
    public String update(){
        System.out.println("用户更新....");
        return "/home.jsp";
    }


}
