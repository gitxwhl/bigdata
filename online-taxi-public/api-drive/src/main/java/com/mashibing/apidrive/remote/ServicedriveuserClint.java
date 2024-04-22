package com.mashibing.apidrive.remote;

import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DriverUserExistsResposonse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-drive-user")
public interface ServicedriveuserClint {

    @RequestMapping(method = RequestMethod.PUT, value = "/users")
    ResponseResult updateDriveUser(@RequestBody DriverUser driverUser);
    //根据手机号发送验证码
    @RequestMapping(method = RequestMethod.GET,value = "/findUser")
    ResponseResult verificationcode(String driverPhone);
    //根据手机号查询用户
    @RequestMapping(method = RequestMethod.GET,value = "/check-driver/{driverPhone}")
    ResponseResult<DriverUserExistsResposonse> getDriverUserbyPhone(@PathVariable("driverPhone") String driverPhone);





}
