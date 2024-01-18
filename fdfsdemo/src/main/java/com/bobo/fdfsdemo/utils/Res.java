package com.bobo.fdfsdemo.utils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.io.Serializable;

/**
 *   接口返回数据格式
 * @author scott
 * @email jeecgos@163.com
 * @date  2019年1月19日
 */
@Data
public class Res<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功标志
     */
    private boolean success = true;

    /**
     * 返回处理消息
     */
    private String message = "";

    /**
     * 返回代码
     */
    private Integer code = 0;

    /**
     * 返回数据对象 data
     */
    private T Res;

    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();

    public Res() {
    }

    /**
     * 兼容VUE3版token失效不跳转登录页面
     * @param code
     * @param message
     */
    public Res(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Res<T> success(String message) {
        this.message = message;
        this.code = CommonConstant.SC_OK_200;
        this.success = true;
        return this;
    }

    public static<T> Res<T> ok() {
        Res<T> r = new Res<>();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_200);
        return r;
    }

    public static<T> Res<T> ok(String msg) {
        Res<T> r = new Res<>();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_200);
        //Res OK(String msg)方法会造成兼容性问题 issues/I4IP3D
        r.setRes((T) msg);
        r.setMessage(msg);
        return r;
    }

    public static<T> Res<T> ok(T data) {
        Res<T> r = new Res<>();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_200);
        r.setRes(data);
        return r;
    }

    public static<T> Res<T> OK() {
        Res<T> r = new Res<>();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_200);
        return r;
    }

    /**
     * 此方法是为了兼容升级所创建
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static<T> Res<T> OK(String msg) {
        Res<T> r = new Res<>();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_200);
        r.setMessage(msg);
        //Res OK(String msg)方法会造成兼容性问题 issues/I4IP3D
        r.setRes((T) msg);
        return r;
    }

    public static<T> Res<T> OK(T data) {
        Res<T> r = new Res<>();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_200);
        r.setRes(data);
        return r;
    }

    public static<T> Res<T> OK(String msg, T data) {
        Res<T> r = new Res<>();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_200);
        r.setMessage(msg);
        r.setRes(data);
        return r;
    }

    public static<T> Res<T> error(String msg, T data) {
        Res<T> r = new Res<>();
        r.setSuccess(false);
        r.setCode(CommonConstant.SC_INTERNAL_SERVER_ERROR_500);
        r.setMessage(msg);
        r.setRes(data);
        return r;
    }

    public static<T> Res<T> error(String msg) {
        return error(CommonConstant.SC_INTERNAL_SERVER_ERROR_500, msg);
    }

    public static<T> Res<T> error(int code, String msg) {
        Res<T> r = new Res<>();
        r.setCode(code);
        r.setMessage(msg);
        r.setSuccess(false);
        return r;
    }

    public Res<T> error500(String message) {
        this.message = message;
        this.code = CommonConstant.SC_INTERNAL_SERVER_ERROR_500;
        this.success = false;
        return this;
    }

    /**
     * 无权限访问返回结果
     */
    public static<T> Res<T> noauth(String msg) {
        return error(CommonConstant.SC_JEECG_NO_AUTHZ, msg);
    }

    @JsonIgnore
    private String onlTable;

}
