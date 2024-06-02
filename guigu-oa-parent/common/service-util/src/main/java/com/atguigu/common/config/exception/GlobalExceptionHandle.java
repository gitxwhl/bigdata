package com.atguigu.common.config.exception;

import com.atguigu.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandle {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result errot(Exception e){
        e.printStackTrace();
        return Result.fail().message("执行了全异常处理。。。。。。。");
    }


}
