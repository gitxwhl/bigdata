package com.officeServices.utils;

import lombok.Data;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class ResultMessage<T> {

    public Integer code;
    public String message;
    public T data;

//    //public ResultMessage(Integer code,String message){
//        this(code,message,null);
//    }

    public ResultMessage() {
    }

    public ResultMessage(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultMessage(Integer code, String message, T data) {
        this.code = code;
        this.message =message;
        this.data = data;
    }



}
