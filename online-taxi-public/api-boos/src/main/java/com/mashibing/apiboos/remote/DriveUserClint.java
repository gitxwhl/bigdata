package com.mashibing.apiboos.remote;

import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-drive-user")
public interface DriveUserClint {

    //添加用户
    @RequestMapping("/users")
    ResponseResult addDriverUser(@RequestBody DriverUser driverUser);
    //修改用户
    @RequestMapping(method = RequestMethod.PUT,value = "/users")
    ResponseResult updateDriverUser(@RequestBody DriverUser driverUser);
    //司机车辆绑定
    @RequestMapping(method = RequestMethod.POST,value = "/bind")
    ResponseResult bind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship);
    //司机车辆解绑
    @RequestMapping(method=RequestMethod.POST,value="/unbind")
    ResponseResult unbind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship);
    //添加车辆信息
    @RequestMapping(method= RequestMethod.POST,value = "/car")
    ResponseResult addCar(@RequestBody Car car);

}
