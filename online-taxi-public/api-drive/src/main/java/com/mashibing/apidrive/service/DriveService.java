package com.mashibing.apidrive.service;

import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;

public interface DriveService {
    //编辑用户信息
    ResponseResult updateDrive(DriverUser driverUser);
}
