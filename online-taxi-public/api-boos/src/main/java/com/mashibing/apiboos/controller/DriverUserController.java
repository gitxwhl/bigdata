package com.mashibing.apiboos.controller;
import com.mashibing.apiboos.service.DriverUserService;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DriverUserController {
    @Autowired
    private DriverUserService driverUserService;


    @RequestMapping("/test")
    public String test(){
        return "api-boos";
    }


    /**
     * 添加司机
     * @param driverUser
     * @return
     */
    @PostMapping("/driver-user")
    public ResponseResult addDriverUser(@RequestBody DriverUser driverUser){

        return driverUserService.addDriverUser(driverUser);
    }
    /**
     * 修改司机信息
     */
    @PutMapping("/driver-user")
    public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser){
        return driverUserService.updateDriverUser(driverUser);
    }

    /**
     * 司机车辆绑定
     */
    @PostMapping("/bind")
    public ResponseResult bind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship){
        return driverUserService.bind(driverCarBindingRelationship);
    }
    /**
     * 司机车辆解绑
     */
    @PostMapping("/unbind")
    public ResponseResult unbind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship){
        return driverUserService.unbind(driverCarBindingRelationship);
    }

}
