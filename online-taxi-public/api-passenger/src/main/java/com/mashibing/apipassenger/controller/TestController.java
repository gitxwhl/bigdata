package com.mashibing.apipassenger.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/test")
    public String getTest(){
        return "测试";
    }

    /**
     * 需要token
     * @return
     */
    public ResponseResult authTest(){

        return ResponseResult.success("auth success");
    }

    /**
     * 没有token正常返回
     * @return
     */
    public ResponseResult noauthTest(){

        return ResponseResult.success("noauth  test");
    }



}
