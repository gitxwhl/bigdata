package com.mashibing.driveuser.service;

import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;

public interface DriverUserService {


     ResponseResult findDriverUser();

     //插入用户的信息

     ResponseResult addDriverUser(DriverUser driverUser);

     //修改司机信息
     ResponseResult updateUser(DriverUser driverUser);
}
