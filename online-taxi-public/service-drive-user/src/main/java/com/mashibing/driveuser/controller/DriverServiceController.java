package com.mashibing.driveuser.controller;
import com.mashibing.driveuser.pojo.DriverUser;
import com.mashibing.driveuser.service.DriverUserService;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverServiceController {
    @Autowired
    private DriverUserService driverUserService;

    @RequestMapping("/findTest")
    public String findTest() {
        return "service-drive-user";
    }


    @RequestMapping("/findDriverService")
    public ResponseResult findDriverService(){
        return driverUserService.findDriverUser();
    }

    /**
     * 添加司机
     * @return
     */
    @RequestMapping("/users")
    public ResponseResult addDriverUser(@RequestBody DriverUser driverUser){
        return driverUserService.addDriverUser(driverUser);
    }



}