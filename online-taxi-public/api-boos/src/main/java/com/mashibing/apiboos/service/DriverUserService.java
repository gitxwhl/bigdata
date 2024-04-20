package com.mashibing.apiboos.service;

import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;

public interface DriverUserService {
    //添加司机
    ResponseResult addDriverUser(DriverUser driverUser);

    //根据司机id修改司机信息
    ResponseResult updateDriverUser(DriverUser driverUser);

    //司机车辆绑定
    ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship);

    //司机车辆解绑
    ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationship);


}
