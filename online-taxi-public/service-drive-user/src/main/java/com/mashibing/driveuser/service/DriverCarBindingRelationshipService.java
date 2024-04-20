package com.mashibing.driveuser.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.ResponseResult;

public interface DriverCarBindingRelationshipService extends IService<DriverCarBindingRelationship> {

    ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship);

    ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationship);

}
