package com.mashibing.service;
import com.mashibing.internalcommon.dto.ResponseResult;

public interface UserService {
    //根据手机号查询用户信息
    ResponseResult loginOrRegister(String verificationDTO);


    ResponseResult getUserbyphone(String phone);


}
