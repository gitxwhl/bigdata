package com.property.utils;

import lombok.Data;

import java.util.Map;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class ResultMessage<T> {

    public Integer code;
    public String message;
    public T data;
    public Map map;

//    //public ResultMessage(Integer code,String message){
//        this(code,message,null);
//    }

    public ResultMessage() {
    }

    public ResultMessage(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultMessage(Integer code, String message, T data, Map map) {
        this.code = code;
        this.message =message;
        this.data = data;
        this.map = map;
    }



}
