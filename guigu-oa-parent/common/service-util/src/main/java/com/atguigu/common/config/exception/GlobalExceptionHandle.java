package com.atguigu.common.config.exception;

import com.atguigu.common.result.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



@ControllerAdvice
public class GlobalExceptionHandle {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result errot(Exception e){
        e.printStackTrace();
        return Result.fail().message("执行了全局异常处理。。。。。。。");
    }
    //特定异常处理：如果有特定的异常执行全局特定的异常，否则，执行全局的异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e){
        e.printStackTrace();
        return Result.fail().message("执行了特定异常处理。。。。。。。");
    }

    @ExceptionHandler(GuiguException.class)
    @ResponseBody
    public Result error(GuiguException e){
        e.printStackTrace();
        return Result.fail().message(e.getMsg()).code(e.getCode());
    }
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result error(AccessDeniedException e) {
        e.printStackTrace();
        return Result.fail().code(205).message("没有操作权限");
    }

}
