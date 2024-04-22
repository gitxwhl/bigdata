package com.mashibing.apidrive.service.impl;

import com.mashibing.apidrive.remote.ServicedriveuserClint;
import com.mashibing.apidrive.remote.VerificationcodeClint;
import com.mashibing.apidrive.service.VerificationcodeService;
import com.mashibing.internalcommon.constent.CommonStatusEnum;
import com.mashibing.internalcommon.constent.DriverCarConstans;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DriverUserExistsResposonse;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VerificationcodeServiceImpl implements VerificationcodeService {
    @Autowired
    private ServicedriveuserClint servicedriveuserClint;
    @Autowired
    private VerificationcodeClint verificationcodeClint;



    //根据手机号发送验证码
    @Override
    public ResponseResult verificationcode(String driverPhone) {
        //根据手机号查询用户
        ResponseResult<DriverUserExistsResposonse> driverUser = servicedriveuserClint.getDriverUserbyPhone(driverPhone);
        DriverUserExistsResposonse data = driverUser.getData();
        String driverPhone1 = data.getDriverPhone();
        int isExists = data.getIsExists();
        if(isExists == DriverCarConstans.NOT_EXISTS){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXISTS.getCode(), CommonStatusEnum.DRIVER_NOT_EXISTS.getValue());
        }
        log.info(driverPhone1 + "司机存在");
        //存在，调用生成验证码服务
        ResponseResult<NumberCodeResponse> verificationcode = verificationcodeClint.getNumberCode(6);
        NumberCodeResponse response = verificationcode.getData();
        int numberCode = response.getNumberCode();
        log.info( "验证码"+ numberCode);

        //调用短信运营商


        //存入redis
        return ResponseResult.success("");
    }


}
