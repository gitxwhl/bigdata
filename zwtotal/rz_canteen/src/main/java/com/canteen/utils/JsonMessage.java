package com.canteen.utils;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用的返回的类
 *
 * @author a1215
 *
 */
public class JsonMessage {
    // 状态码 100-成功 200-失败类型1 201-失败类型2  202-失败类型3
    private int code;
    // 提示信息
    private String message;

    // 用户要返回给浏览器的数据
    private Map<String, Object> extend = new HashMap<String, Object>();

    public static JsonMessage success() {
        JsonMessage result = new JsonMessage();
        result.setCode(100);
        result.setMessage("处理成功！");
        return result;
    }

    public static JsonMessage fail1() {
        JsonMessage result = new JsonMessage();
        result.setCode(200);
        result.setMessage("处理失败！，失败类型1");
        return result;
    }

    public static JsonMessage fail2() {
        JsonMessage result = new JsonMessage();
        result.setCode(201);
        result.setMessage("处理失败！，失败类型2");
        return result;
    }

    public static JsonMessage fail3() {
        JsonMessage result = new JsonMessage();
        result.setCode(202);
        result.setMessage("处理失败！，失败类型3");
        return result;
    }

    public JsonMessage add(String key, Object value) {
        this.getExtend().put(key, value);
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }

}
