package com.mashibing.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.*;

public class JwtUtils {


    //盐
    private static final String SIGN="GFKLSDFJ" ;
    private static final String JWT_KEY="passengerPhone";

    //生成token
    public static String generatorToken(String passengerPhone){
        Map<String,String> map =new HashMap<>();
        map.put(JWT_KEY,passengerPhone);
        //token 过期时间  当前时间加一天
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        Date date = calendar.getTime();
         JWTCreator.Builder builder = JWT.create();
        //整合map  map取出k，v的值逐个放到
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        //整合过期时间
        builder.withExpiresAt(date);
        //生成token
       String sign = builder.sign(Algorithm.HMAC256(SIGN));
        return sign;
    }


    //解析token
    public static String parseToken(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        Claim claim = verify.getClaim(JWT_KEY);
        return claim.toString();
    }





    public static void main(String[] args) {
        //生成tooken
       String s = generatorToken("15252125537");
        System.out.println("生成的token：" + s);
        //解析tooken
        String parseToken  =  parseToken(s);
        System.out.println("------解析tooen----------->" + parseToken);

    }

}
