package com.bobo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
//**SpringSecurity提供的注解**
@Controller
@RequestMapping("/role")
public class RoleController {

    /**
     * @Secured  定义接口所需要的权限
     * @return
     */
  @Secured("ROLE_USER")
  @RequestMapping("/query")
  public String query(){
    System.out.println("用户查询....");
    return "/home.jsp";
   }

  @Secured("ROLE_ADMIN")
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
