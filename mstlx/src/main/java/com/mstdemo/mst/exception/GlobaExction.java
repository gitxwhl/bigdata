package com.mstdemo.mst.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobaExction {
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public CommonResult exctptionHandler(){
        return new CommonResult(200, "系统操作失败");
    }
}
