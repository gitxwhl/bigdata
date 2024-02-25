package com.mashibing.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mashibing.internalcommon.constent.TokenResult;
import com.mashibing.internalcommon.constent.TookenConstent;

import java.util.*;

public class JwtUtils {


    //盐
    private static final String SIGN="GFKLSDFJ" ;
    private static final String JWT_KEY_PHONE="phone";
    //1代表乘客   2代表司机
    private static final String JWT_KEY_IDENTITY="identity";

    //token类型
    private static final String TOOKEN_TYPE="tookeType";

    private static final String JWT_TOOKEN_TIME ="tookenTime";


    //生成token
    public static String generatorToken(String passengerPhone,String identity,String tookenType){
        Map<String,String> map =new HashMap<>();
        map.put(JWT_KEY_PHONE,passengerPhone);
        map.put(JWT_KEY_IDENTITY,identity);
        map.put(TOOKEN_TYPE, tookenType);
        //token 过期时间  当前时间加一天
        /*Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        Date date = calendar.getTime();*/
        //防止每次生成的tooken一样
        map.put(JWT_TOOKEN_TIME, Calendar.getInstance().getTime().toString());
         JWTCreator.Builder builder = JWT.create();
        //整合map  map取出k，v的值逐个放到
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        //整合过期时间
//        builder.withExpiresAt(date);
        //生成token
       String sign = builder.sign(Algorithm.HMAC256(SIGN));
        return sign;
    }


    //解析token
    public static TokenResult parseToken(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        String phone = verify.getClaim(JWT_KEY_PHONE).asString();
        String identity  = verify.getClaim(JWT_KEY_IDENTITY).asString();
        TokenResult tokenResult =new TokenResult();
        tokenResult.setPhone(phone);
        tokenResult.setIdentity(identity);
        return tokenResult;
    }

    /**
     * 校验tooken，判断tooken是否异常
     * @param tooken
     */
    public static TokenResult checkTooken(String tooken){
        TokenResult  resulttooken=null;
        try {
            //解析tooken
              resulttooken =  JwtUtils.parseToken(tooken);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resulttooken;

    }




    public static void main(String[] args) {
        //生成tooken
       String s = generatorToken("15252125537","1", TookenConstent.ACCESS_TOOKEN_TYPE);
        System.out.println("生成的token：" + s);
        //解析tooken
        TokenResult parseToken  =  parseToken(s);
        System.out.println("------解析tooen----------->" + parseToken.getPhone() + "身份" + parseToken.getIdentity());

    }

}
