package com.mashibing.driveuser.controller;


import com.mashibing.driveuser.service.DriverCarBindingRelationshipService;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DriverCarBindingRelationshipController {
    @Autowired
   private DriverCarBindingRelationshipService driverCarBindingRelationshipService;
    /**
     *司机车辆 绑定
     * @param driverCarBindingRelationship
     * @return
     */
    @RequestMapping("/bind")
    public ResponseResult bind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship){
        return driverCarBindingRelationshipService.bind(driverCarBindingRelationship);
    }
    /**
     * 司机车辆解绑
     */
    @RequestMapping("/unbind")
    public ResponseResult unbind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship){

        return driverCarBindingRelationshipService.unbind(driverCarBindingRelationship);
    }


}
