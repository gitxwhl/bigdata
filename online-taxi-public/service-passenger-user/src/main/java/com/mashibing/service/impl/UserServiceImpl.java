package com.mashibing.service.impl;

import com.mashibing.dto.PassengerUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.mapper.PassengerUserMapper;
import com.mashibing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PassengerUserMapper passengerUserMapper;




    /**
     * 根据手机号查询用户信息
     */
    @Override
    public ResponseResult loginOrRegister(String passengerPhone) {
        System.out.println("UserService 被调用 手机号   "+ passengerPhone);
       //判断根据手机号获取用户信息
        Map<String,Object> map= new HashMap();
        map.put("passenger_phone",passengerPhone);
        //判断用户是否存在
        List<PassengerUser> PassengerUserlist = passengerUserMapper.selectByMap(map);
        System.out.println(PassengerUserlist.size()==0 ? "无记录" : PassengerUserlist.get(0).getPassengerName());
        String passengerName = PassengerUserlist.size()==0 ? "无记录" : PassengerUserlist.get(0).getPassengerName();
        //不存在，插入用户信息
        if(PassengerUserlist.size()==0){
            PassengerUser passengerUser =new PassengerUser();
            passengerUser.setPassengerName("张三");
            passengerUser.setPassengerPhone(passengerPhone);
            passengerUser.setPassengerGender((byte)0);
            LocalDateTime now = LocalDateTime.now();
            passengerUser.setGmtCreate(now.toString());
            passengerUser.setGmtModified(now.toString());
            passengerUser.setState((byte)0);
            //添加用户
            passengerUserMapper.insert(passengerUser);
        }
        return ResponseResult.success();
    }
}
