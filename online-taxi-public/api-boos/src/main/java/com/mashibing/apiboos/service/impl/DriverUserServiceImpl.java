package com.mashibing.apiboos.service.impl;
import com.mashibing.apiboos.remote.DriveUserClint;
import com.mashibing.apiboos.service.DriverUserService;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverUserServiceImpl implements DriverUserService {
    @Autowired
    private DriveUserClint driveUserClint;
    @Override
    public ResponseResult addDriverUser(DriverUser driverUser) {
        return driveUserClint.addDriverUser(driverUser);
    }

    @Override
    public ResponseResult updateDriverUser(DriverUser driverUser) {
        return driveUserClint.updateDriverUser(driverUser);
    }

    @Override
    public ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship) {
        return driveUserClint.bind(driverCarBindingRelationship);
    }

    @Override
    public ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationship) {
        return driveUserClint.unbind(driverCarBindingRelationship);
    }


}
