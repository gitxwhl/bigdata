package com.mashibing.apipassenger.service.impl;

import com.mashibing.apipassenger.service.TookenService;
import com.mashibing.internalcommon.constent.CommonStatusEnum;
import com.mashibing.internalcommon.constent.ConstentIdentity;
import com.mashibing.internalcommon.constent.TokenResult;
import com.mashibing.internalcommon.constent.TookenConstent;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResponse;
import com.mashibing.internalcommon.util.JwtUtils;
import com.mashibing.internalcommon.util.RedisPrefixUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
public class TookenServiceImpl implements TookenService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public ResponseResult refreshTooken(String refreshTookenSrc) {

        //解析refreshTooken
        TokenResult resulttooken = JwtUtils.checkTooken(refreshTookenSrc);
        if(resulttooken==null){
           return ResponseResult.fail(CommonStatusEnum.TOOKEN_ERROR.getCode(), CommonStatusEnum.TOOKEN_ERROR.getValue());
        }
        String phone = resulttooken.getPhone();
        String identity = resulttooken.getIdentity();
        //读取redis中的 refreshTooken
        String refreshKey = RedisPrefixUtils.getGenraTooken(phone, ConstentIdentity.PASSENGER_IDENTITY, TookenConstent.REFRESH_TOOKEN_TYPE);
        String reFreshTooken= stringRedisTemplate.opsForValue().get(refreshKey);
        //校验refreshTooken
        if(StringUtils.isBlank(reFreshTooken) || !reFreshTooken.trim().equals(refreshTookenSrc.trim())){
        return ResponseResult.fail(CommonStatusEnum.TOOKEN_ERROR.getCode(), CommonStatusEnum.TOOKEN_ERROR.getValue());
        }
        //生成双tooken
        String accessToken = JwtUtils.generatorToken(phone, ConstentIdentity.PASSENGER_IDENTITY,TookenConstent.ACCESS_TOOKEN_TYPE);
        String refreshToken = JwtUtils.generatorToken(phone, ConstentIdentity.PASSENGER_IDENTITY, TookenConstent.REFRESH_TOOKEN_TYPE);
        //将tooken存入redis
        String accessKey = RedisPrefixUtils.getGenraTooken(phone, ConstentIdentity.PASSENGER_IDENTITY, TookenConstent.ACCESS_TOOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(accessKey,accessToken,30,TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(refreshKey, refreshToken, 31, TimeUnit.DAYS);

//        stringRedisTemplate.opsForValue().set(accessKey,accessToken,10,TimeUnit.SECONDS);
//        stringRedisTemplate.opsForValue().set(refreshKey, refreshToken, 50, TimeUnit.SECONDS);
        //将刷新tooken和访问tooken返回
        TokenResponse tokenResponse=new TokenResponse();
        tokenResponse.setRefreshToken(refreshToken);
        tokenResponse.setAccessToken(accessToken);
        return ResponseResult.success(tokenResponse);
    }
}
