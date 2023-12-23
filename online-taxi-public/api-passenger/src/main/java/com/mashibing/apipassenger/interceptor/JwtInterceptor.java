package com.mashibing.apipassenger.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.util.JwtUtils;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        //返回值
        boolean result=true;
        //返回的错误内容
        String resultString="";

//        获取请求头中的tooken
          String tooken  = request.getHeader("Authorization");
          try {
              JwtUtils.parseToken(tooken);
          }catch (SignatureVerificationException e){
            resultString="tooken sign erro";
            result = false;
          }catch (TokenExpiredException e){
            resultString="tooken time out";
            result=false;
          }catch (AlgorithmMismatchException e){
            resultString ="AlgorithmMismatchException error";
            result=false;
          }
          catch (Exception e){
              resultString = "tooken invalid";
              result =  false;
          }
        // 如果解析报错将错误信息返回给前台
        if(!result){
            PrintWriter out = response.getWriter();
            out.print(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
        }


        return result;
    }



}
