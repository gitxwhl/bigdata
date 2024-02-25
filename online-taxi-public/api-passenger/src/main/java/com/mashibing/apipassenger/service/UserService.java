package com.mashibing.apipassenger.service;

import com.mashibing.internalcommon.dto.ResponseResult;

public interface UserService {

     ResponseResult getUserByPhone(String accesstoken);
}
