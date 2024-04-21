package com.mashibing.apidrive.service.impl;
import com.mashibing.apidrive.remote.ServicedriveuserClint;
import com.mashibing.apidrive.service.DriveService;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class DriveServiceImpl implements DriveService {
    @Autowired
    private ServicedriveuserClint servicedriveuserClint;
    //编辑用户信息
    @Override
    public ResponseResult updateDrive(DriverUser driverUser) {
        return servicedriveuserClint.updateDriveUser(driverUser);
    }

}
