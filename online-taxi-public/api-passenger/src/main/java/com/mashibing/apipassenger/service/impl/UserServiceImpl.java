package com.mashibing.apipassenger.service.impl;

import com.mashibing.apipassenger.service.UserService;
import com.mashibing.internalcommon.dto.PassengerUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    /**
     * 根据手机号获取用户信息
     * @return
     */
    @Override
    public ResponseResult getUserByPhone(String accesstoken) {
        //解析accesstoken拿到手机号


        //根据手机号获取用户信息


        PassengerUser passengerUser=new PassengerUser();
        passengerUser.setPassengerName("张三");
        passengerUser.setPassengerFileUrl("www.url.com");
        return ResponseResult.success(passengerUser);
    }
}
