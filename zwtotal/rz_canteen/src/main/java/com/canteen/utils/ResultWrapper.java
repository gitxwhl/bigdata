package com.canteen.utils;

import org.springframework.http.HttpStatus;

import java.util.HashMap;


public class ResultWrapper extends  HashMap<String,Object> {
    private static final long serialVersionUID = -8713837118340960775L;

    public ResultWrapper code(HttpStatus status) {
        this.put("code", status.value());
        return this;
    }

    public ResultWrapper message(String message) {
        this.put("message", message);
        return this;
    }

    public ResultWrapper data(Object data) {
        this.put("data", data);
        return this;
    }

    public ResultWrapper success() {
        this.code(HttpStatus.OK);
        return this;
    }

    public ResultWrapper fail() {
        this.code(HttpStatus.INTERNAL_SERVER_ERROR);
        return this;
    }

    public ResultWrapper put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
