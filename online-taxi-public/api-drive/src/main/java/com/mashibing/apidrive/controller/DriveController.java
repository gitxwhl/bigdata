package com.mashibing.apidrive.controller;

import com.mashibing.apidrive.service.DriveService;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DriveController {
    @Autowired
    private DriveService driveService;

    /**
     * 根据用户id 编辑司机信息
     */
    @PutMapping("/drive-usre")
    public ResponseResult updateDrive(@RequestBody DriverUser driverUser) {
        return driveService.updateDrive(driverUser);
    }
}
