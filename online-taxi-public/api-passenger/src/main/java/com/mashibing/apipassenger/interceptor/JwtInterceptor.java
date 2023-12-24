package com.mashibing.apipassenger.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.mashibing.internalcommon.constent.TokenResult;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.util.JwtUtils;
import com.mashibing.internalcommon.util.RedisPrefixUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        //返回值
        boolean result=true;
        //返回的错误内容
        String resultString="";

//        获取请求头中的tooken
          String tooken  = request.getHeader("Authorization");
        TokenResult resulttooken= null;
          try {
              //解析tooken
              resulttooken =  JwtUtils.parseToken(tooken);
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

        String phone= resulttooken.getPhone();
        String identiry = resulttooken.getIdentity();
        //拼接key
        String tookenKey = RedisPrefixUtils.getGenraTooken(phone,identiry);
        //根据tooken key值 ，从redis中取出tooken
        String redisTooken = stringRedisTemplate.opsForValue().get(tookenKey);
        if(StringUtils.isBlank(redisTooken)){
            resultString = "tooken invalid";
            result =  false;
        }else {
            //比较我们传入的tooken和redis中的tooken是否相等
            if(!redisTooken.trim().equals(tooken.trim())){
                resultString = "tooken invalid";
                result =  false;
            }
        }

        // 如果解析报错将错误信息返回给前台
        if(!result){
            PrintWriter out = response.getWriter();
            out.print(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
        }


        return result;
    }



}
