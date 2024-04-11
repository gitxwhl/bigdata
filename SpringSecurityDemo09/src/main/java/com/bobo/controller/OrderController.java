package com.bobo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
//spring 的表达式
@Controller
@RequestMapping("/order")
public class OrderController {
  /**
   * @PreAuthorize  定义接口所需要的权限
   * @return
   */
  @PreAuthorize(value = "hasAnyRole('ROLE_USER')")
  @RequestMapping("/query")
  public String query(){
    //获取用户
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    UserDetails userDetails = (UserDetails)principal;
    System.out.println(userDetails.getUsername());
    System.out.println(userDetails.getPassword());
    //获取用户所有的权限
    for (GrantedAuthority authority :userDetails.getAuthorities()){
      System.out.println("当前用户的权限为：" + authority.getAuthority());
    }
    System.out.println("用户查询....");
    return "/home.jsp";
   }
  @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN')")
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
