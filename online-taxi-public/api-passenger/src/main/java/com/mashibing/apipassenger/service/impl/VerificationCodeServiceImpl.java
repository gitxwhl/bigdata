package com.mashibing.apipassenger.service.impl;

import com.mashibing.apipassenger.service.VerificationCodeService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {


    /**
     * 获取验证码
     * @param code
     * @return
     */
    @Override
    public String getVerificationCode(String code) {
        //调用验证码服务，获取验证




        //存入redis



        //返回值
        JSONObject json =  new JSONObject();
        json.put("code",1);
        json.put("message","success");
        return json.toString();
    }
}
