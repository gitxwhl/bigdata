//*********************************************************************
//系统名称：PI6000 应用开发平台
//Copyright(C)2015 NARI Information and Communication Technology Branch. All rights reserved.
//版本信息：PI6000-V1.0。0
//#作者:郭伟 权重:100% 手机:13770754594单位:南瑞信通#
//#创建时间:2017年4月6日 下午1:32:49
//类说明
//*********************************************************************
package com.raysdata.riskdataanalyzeserver.utils;

import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * JWT的工具类
 *
 * @author Guowei
 */
public class JWTUtil {
    //默认的token有效期为30分钟
    static long DEFAULT_TTL = 60 * 30L;

    public static String createJWT(String userId, String name) {
        return createJWT(userId, name, DEFAULT_TTL);
    }

    public static String createJWT(String userId, String name, long ttl) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Key key = generalKey();
        JwtBuilder builder = Jwts.builder().setId(userId).setIssuedAt(now)
                .setSubject(name)
                .signWith(signatureAlgorithm, key);

        if (ttl >= 0) {
            long expMillis = nowMillis + ttl * 1000;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    public static String createJWT(String userId, String name, String fullName, long ttl) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Key key = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(userId)
                .setIssuedAt(now)
                .setSubject(name)
                .setAudience(fullName)
                .signWith(signatureAlgorithm, key);

        if (ttl >= 0) {
            long expMillis = nowMillis + ttl * 1000;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    public static String createRefreshToken(String userId, String name, String fullName, long jwtTtl, long rtTtl) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Key key = generalRTKey();

        JwtBuilder builder = Jwts.builder()
                .setId(userId)
                .setIssuedAt(now)
                .setSubject(name)
                .setHeaderParam("ttl", jwtTtl)
                .setAudience(fullName)
                .signWith(signatureAlgorithm, key);

        if (rtTtl >= 0) {
            long expMillis = nowMillis + rtTtl * 1000;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    public static Jws<Claims> parseRefreshToken(String rt) {
        Key key = generalRTKey();
        Jws<Claims> jws = Jwts.parser().setSigningKey(key).parseClaimsJws(rt);
        return jws;
    }

    public static String validateRefreshToken(String rt) {
        Jws<Claims> jws = parseRefreshToken(rt);
        return createJWT(jws.getBody().getId(), jws.getBody().getSubject(), jws.getBody().getAudience(), Long.parseLong(jws.getHeader().get("ttl").toString()));
    }

    //原来未修改
//    public static Claims parseJWT(String jwt) {
//        Key key = generalKey();
//        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
//        return claims;
//    }






    public static Claims parseJwt(String token){
        Key key = generalKey();
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(key) // 设置标识名
                    .parseClaimsJws(token)  //解析token
                    .getBody();
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        }
        return claims;
    }


    public Boolean isTokenExpired(String token) {
        //不管是否过期，都返回claims对象
        Claims claims = parseJwt(token);
        Date expiration = claims.getExpiration();
        //和当前时间进行对比来判断是否过期
        return new Date(System.currentTimeMillis()).after(expiration);
    }




//原来未修改
//    public static boolean validateJWT(String jwt) {
//        try {
//            parseJWT(jwt);
//            return true;
//        } catch (Exception ex) {
//            return false;
//        }
//    }

    public static Key generalKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("memDataSource");
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    public static Key generalRTKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("dataSource");
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }
}
