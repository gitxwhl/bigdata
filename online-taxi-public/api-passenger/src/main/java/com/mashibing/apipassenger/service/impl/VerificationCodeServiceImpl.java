package com.mashibing.apipassenger.service.impl;

import com.mashibing.apipassenger.remote.ServiceverifiCationcodeClint;
import com.mashibing.apipassenger.service.VerificationCodeService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {
        @Autowired
       private ServiceverifiCationcodeClint serviceverifiCationcodeClint;
//       乘客验证码前缀
        private String  verificationCodePrefix="passenge-verification-code-";
        @Autowired
        private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取验证码
     * @param
     * @return
     */
    @Override
    public ResponseResult getVerificationCode(String passengerPhone) {
        //调用验证码服务，获取验证
        ResponseResult<NumberCodeResponse> numCode = serviceverifiCationcodeClint.getNumberCode(5);
        int code= numCode.getData().getNumberCode();
        //添加乘客手机号前缀
        String key = verificationCodePrefix + passengerPhone;
        //存入redis  key，value,过期时间
        stringRedisTemplate.opsForValue().set(key,code+"",2, TimeUnit.MINUTES);
        //通过短信服务商，将对应的验证码发送到手机上，阿里短信服务，腾讯短信通，华信，容联




        return ResponseResult.success();
    }
}
