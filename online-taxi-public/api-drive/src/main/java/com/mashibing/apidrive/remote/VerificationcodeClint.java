package com.mashibing.apidrive.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-verificationcode")
public interface VerificationcodeClint {

    //生成验证码
    @RequestMapping(method = RequestMethod.GET,value = "/numberCode/{size}")
    ResponseResult<NumberCodeResponse> getNumberCode(@PathVariable("size") int size);


}
