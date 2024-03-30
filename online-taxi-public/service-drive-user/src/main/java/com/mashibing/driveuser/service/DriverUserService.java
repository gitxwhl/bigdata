package com.mashibing.driveuser.service;

import com.mashibing.driveuser.pojo.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;

public interface DriverUserService {


     ResponseResult findDriverUser();

     //插入用户的信息

     ResponseResult addDriverUser(DriverUser driverUser);
}
