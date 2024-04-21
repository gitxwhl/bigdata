package com.mashibing.apidrive.service.impl;

import com.mashibing.apidrive.remote.ServicedriveuserClint;
import com.mashibing.apidrive.service.VerificationcodeService;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationcodeServiceImpl implements VerificationcodeService {
    @Autowired
    private ServicedriveuserClint servicedriveuserClint;

    //根据手机号发送验证码
    @Override
    public ResponseResult verificationcode(String driverPhone) {
        //根据手机号查询用户


        //存在，调用生成验证码服务

        //调用短信运营商


        //存入redis


        return ResponseResult.success("");
    }


}
