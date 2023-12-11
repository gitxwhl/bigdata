package com.mashibing.internalcommon.dto;

import com.mashibing.internalcommon.constent.CommonStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 包装统一的返回值
 * Accessors  表示设置完一个值之后还会返回一个对象接着调用
 */
@Data
@Accessors(chain = true)
public class ResponseResult<T> {

    private int code;
    private String message;
    private T data;


    /**
     * 无参返回成功
     * @param <T>
     * @return
     */
    public static <T> ResponseResult success(){
        return new ResponseResult().setCode(CommonStatusEnum.SUCESS.getCode()).setMessage(CommonStatusEnum.SUCESS.getValue());
    }


    /**
     * 成功的相应方法
     * 自定义的泛型方法
     */
    public static <T> ResponseResult success(T data){
    return new ResponseResult().setCode(CommonStatusEnum.SUCESS.getCode()).setMessage(CommonStatusEnum.SUCESS.getValue()).setData(data);
    }
    /**
     * 失败响应的方法
     * 第一种：默认响应的失败
     */

    /**
     * 自定义失败：不需要有data：错误码和提示信息
     */

    public static ResponseResult fail(int code,String message){
        return new ResponseResult().setCode(code).setMessage(message);
    }
    /**
     * code,maessage,data都是自定义的失败
     * 如果调用其他服务返回的信息可以放在data里面
     */
    public static ResponseResult fail (int code,String message,String data){
        return new ResponseResult().setCode(code).setMessage(message).setData(data);
    }

    /**
     * 统一的失败，默认，不建议使用
     */
    public static <T> ResponseResult fail(T data){
    return new ResponseResult().setData(data);

    }






}
