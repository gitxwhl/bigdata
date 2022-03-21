package com.canteen.utils;

import com.google.gson.Gson;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;

public class HttpUtils {
    private Gson gson = new Gson();
    private String jsonValue = null;

    private String getJson(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (jsonValue == null) {
            resp.setContentType("application/json;charset=utf-8");
            //获取json数据(ServletInputStream只能读一次数据)
            ServletInputStream is = req.getInputStream();

            byte[] buffer = new byte[1024];
            StringBuffer sb = new StringBuffer();
            while ((is.read(buffer) != -1)) {
                sb.append(new String(buffer));
            }
            return jsonValue = sb.toString().trim();
        } else {
            return jsonValue;
        }

    }

    public Object getBean(HttpServletRequest req, HttpServletResponse resp, Class<Object> clazz) throws IOException {
        Object obj = gson.fromJson(getJson(req, resp), clazz);
        return obj;
    }

    public Object getBean(HttpServletRequest req, HttpServletResponse resp, Type type) throws IOException {
        Object obj = gson.fromJson(getJson(req, resp), type);
        return obj;
    }

    public String toJson(Object obj) {
        return gson.toJson(obj);
    }
}
