package com.mashibing.apipassenger.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("service-verificationcode")
@Component
public interface ServiceverifiCationcodeClint {

    /**
    *接收手机号调用验证码服务
     */
    @RequestMapping("/numberCode/{size}")
     ResponseResult<NumberCodeResponse> getNumberCode(@PathVariable("size") int size);



}
