package com.mstdemo.mst.exception;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GlobaExction {
    /**
     * 添加校验参数异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public CommonResult bindExceptionHandler(BindException e){
        return new CommonResult(500, "参数绑定异常",e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public CommonResult exctptionHandler(){
        return new CommonResult(500, "系统操作失败");
    }
}
