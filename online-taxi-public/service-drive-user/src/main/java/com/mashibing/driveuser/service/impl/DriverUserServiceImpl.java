package com.mashibing.driveuser.service.impl;

import com.mashibing.driveuser.mapper.DriverUserMapper;
import com.mashibing.driveuser.pojo.DriverUser;
import com.mashibing.driveuser.service.DriverUserService;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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






}
