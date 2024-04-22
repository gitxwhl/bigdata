package com.mashibing.driveuser.service.impl;

import com.mashibing.driveuser.mapper.DriverUserMapper;
import com.mashibing.driveuser.service.DriverUserService;
import com.mashibing.internalcommon.constent.CommonStatusEnum;
import com.mashibing.internalcommon.constent.DriverCarConstans;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class DriverUserServiceImpl implements DriverUserService {
    @Autowired
    private DriverUserMapper driverUserMapper;


    @Override
    public ResponseResult findDriverUser() {
//        DriverUser driverUser = driverUserMapper.selectById(1);
        Map<String,Object> driverUserMap = new HashMap<>();
        driverUserMap.put("id","1584359006294835202");
        List<DriverUser> driverUsers = driverUserMapper.selectByMap(driverUserMap);
        return ResponseResult.success(driverUsers);
    }



    //插入用户的信息
    @Override
    public ResponseResult addDriverUser(DriverUser driverUser) {
         LocalDateTime now = LocalDateTime.now();
         driverUser.setGmtCreate(now);
         driverUser.setGmtModified(now);
         driverUserMapper.insert(driverUser);
        return ResponseResult.success();
    }


    //根据id修改司机信息
    @Override
    public ResponseResult updateUser(DriverUser driverUser) {
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtModified(now);
        driverUserMapper.updateById(driverUser);
        return ResponseResult.success();
    }

    //查询用户是否存在
    @Override
    public ResponseResult<DriverUser> getDriverUserbyPhone(String driverPhone) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("driver_phone",driverPhone);
        map.put("state", DriverCarConstans.DRIVER_STATE_VALID);
        List<DriverUser> driverUsersList = driverUserMapper.selectByMap(map);
        if(driverUsersList.isEmpty()){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_BIND_NOT_EXISTS.getCode(),CommonStatusEnum.DRIVER_BIND_NOT_EXISTS.getValue());
        }
        DriverUser driveUser = driverUsersList.get(0);
        return ResponseResult.success(driveUser);
    }




}
