package com.mashibing.apipassenger.service.impl;

import com.mashibing.apipassenger.remote.ServicePassengerUserClint;
import com.mashibing.apipassenger.remote.ServiceverifiCationcodeClint;
import com.mashibing.apipassenger.service.VerificationCodeService;
import com.mashibing.internalcommon.constent.CommonStatusEnum;
import com.mashibing.internalcommon.constent.ConstentIdentity;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResponse;
import com.mashibing.internalcommon.request.VerificationDTO;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import com.mashibing.internalcommon.util.JwtUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {
        @Autowired
       private ServicePassengerUserClint servicePassengerUserClint;
        @Autowired
       private ServiceverifiCationcodeClint serviceverifiCationcodeClint;
//       乘客验证码前缀
        private String  verificationCodePrefix="passenge-verification-code-";
        //tooken前缀
        private String to0kenPreFix="tooken-";
        @Autowired
        private StringRedisTemplate stringRedisTemplate;

    /**
     * 生成验证码
     * @param
     * @return
     */
    @Override
    public ResponseResult getVerificationCode(String passengerPhone) {
        //调用验证码服务，获取验证
        ResponseResult<NumberCodeResponse> numCode = serviceverifiCationcodeClint.getNumberCode(5);
        int code= numCode.getData().getNumberCode();
        //添加乘客手机号前缀
        String key = getkeyByphone(passengerPhone);
        //存入redis  key，value,过期时间
        stringRedisTemplate.opsForValue().set(key,code+"",2, TimeUnit.MINUTES);
        //通过短信服务商，将对应的验证码发送到手机上，阿里短信服务，腾讯短信通，华信，容联







        return ResponseResult.success();
    }


    /**
     *生成key
     */
    public String getkeyByphone(String passengerPhone){
        return verificationCodePrefix + passengerPhone;
    }


    /**
     * tooken存入redis
     * @param passengerPhone
     * @param identiy
     * @return
     */
    public String getGenraTooken(String passengerPhone,String identiy){
        return to0kenPreFix + passengerPhone + "-" + identiy;
    }




    /**
     * 校验验证码
     * @param passengerPhone   手机号
     * @param verificationCode  验证码
     * @return
     */
    @Override
    public ResponseResult verificationCodeCheck(String passengerPhone, String verificationCode) {
        //根据手机号从redis里面获取验证码
        //获取key
        String rediskey = getkeyByphone(passengerPhone);
        //根据key获取value
        String code = stringRedisTemplate.opsForValue().get(rediskey);
        //如果验证码为空
        if(StringUtils.isBlank(verificationCode)){
            return  ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERREO.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERREO.getValue());
        }
        //验证码不相等
        if(!verificationCode.trim().equals(code.trim())){
           return  ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERREO.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERREO.getValue());
        }
        //调用用户服务，通过手机号判断数据库里面有没有该用户，如果没有添加用户，如果有返回用户登录信息
        VerificationDTO verificationDTO =new VerificationDTO();
        verificationDTO.setPassengerPhone(passengerPhone);
        servicePassengerUserClint.loginOrRegister(verificationDTO);
        //颁发token
        String token = JwtUtils.generatorToken(passengerPhone, ConstentIdentity.PASSENGER_IDENTITY);
        //将token存入redis
        String tookenKey = getGenraTooken(passengerPhone,ConstentIdentity.PASSENGER_IDENTITY);
        stringRedisTemplate.opsForValue().set(tookenKey,token,30,TimeUnit.DAYS);








        //响应
        TokenResponse tokenResponse=new TokenResponse();
        tokenResponse.setToken(token);
        return ResponseResult.success(tokenResponse);
    }
}
