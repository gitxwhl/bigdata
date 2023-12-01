package com.mashibing.apipassenger.service.impl;

import com.mashibing.apipassenger.remote.ServiceverifiCationcodeClint;
import com.mashibing.apipassenger.service.VerificationCodeService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {
        @Autowired
       private ServiceverifiCationcodeClint serviceverifiCationcodeClint;

    /**
     * 获取验证码
     * @param
     * @return
     */
    @Override
    public String getVerificationCode(String passengerPhone) {
        //调用验证码服务，获取验证
        System.out.println("调用验证码服务，获取验证");
        ResponseResult<NumberCodeResponse> numCode = serviceverifiCationcodeClint.getNumberCode(5);

        int code= numCode.getData().getNumberCode();
        System.out.println("remote name code--------------------->>" + code);
        //存入redis



        //返回值
        JSONObject json =  new JSONObject();
        json.put("code",1);
        json.put("message","success");
        return json.toString();
    }
}
