package com.mashibing.apipassenger.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-passenger-user")
public interface ServicePassengerUserClint {
    //通过手机号判断数据库里面有没有该用户，如果没有添加用户，如果有返回用户登录信息
    @RequestMapping(method = RequestMethod.POST,value = "/user")
     ResponseResult loginOrRegister(@RequestBody VerificationDTO verificationDTO);

    //根据手机号返回用户信息
    @RequestMapping(method = RequestMethod.GET,value = "/user/{phone}")
     ResponseResult getUserByPhone(@PathVariable ("phone")String phone);


}
