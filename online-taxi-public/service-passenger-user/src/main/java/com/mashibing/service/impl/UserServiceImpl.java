package com.mashibing.service.impl;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.service.UserService;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImpl implements UserService {

    /**
     * 根据手机号查询用户信息
     */
    @Override
    public ResponseResult loginOrRegister(String passengerPhone) {
        System.out.println("UserService 被调用 手机号   "+ passengerPhone);
       //判断根据手机号获取用户信息


        //判断用户是否存在


        //不存在，插入用户信息



        return null;
    }
}
