package com.mashibing.apipassenger.service.impl;

import com.mashibing.apipassenger.remote.ServicePassengerUserClint;
import com.mashibing.apipassenger.service.UserService;
import com.mashibing.internalcommon.constent.TokenResult;
import com.mashibing.internalcommon.dto.PassengerUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private ServicePassengerUserClint servicePassengerUserClint;


    /**
             * 根据手机号获取用户信息
             * @return
             */
    @Override
    public ResponseResult getUserByPhone(String accesstoken) {
        //解析accesstoken拿到手机号
        TokenResult tokenResult = JwtUtils.checkTooken(accesstoken);
        String phone = tokenResult.getPhone();
        //根据手机号获取用户信息,调用用户服务
        ResponseResult responseResult = servicePassengerUserClint.getUserByPhone(phone);
        return ResponseResult.success(responseResult.getData());
    }
}
