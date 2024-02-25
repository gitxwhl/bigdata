package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.service.UserService;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 根据手机号获取用户信息
     *
     * @return
     */
    @RequestMapping("/users")
    public ResponseResult getUserByPhone(HttpServletRequest request) {
        //获取accesstoken
        String accesstoken = request.getHeader("Authorization");
        System.out.println("accesstoken======" + accesstoken);
        //解析token信息放入service层
        return userService.getUserByPhone(accesstoken);
    }


}
