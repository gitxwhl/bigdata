package com.mashibing.driveuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashibing.driveuser.mapper.DriverCarBindingRelationshipMapper;
import com.mashibing.driveuser.service.DriverCarBindingRelationshipService;
import com.mashibing.internalcommon.constent.CommonStatusEnum;
import com.mashibing.internalcommon.constent.DriverCarConstans;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DriverCarBindingRelationshipServiceImpl extends ServiceImpl<DriverCarBindingRelationshipMapper, DriverCarBindingRelationship> implements DriverCarBindingRelationshipService {

    @Autowired
    private DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

    /**
     * 司机车辆绑定
     * @param driverCarBindingRelationship
     * @return
     */
    @Override
    public ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship) {
        //绑定关系已经存在，不绑定
        QueryWrapper<DriverCarBindingRelationship> queryWrapper =new QueryWrapper();
        queryWrapper.eq("driver_id",driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("car_id",driverCarBindingRelationship.getCarId());
        queryWrapper.eq("bind_state", DriverCarConstans.DRIVER_CAR_BIND);
        Integer integer = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if(integer.intValue()>0){
          return   ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_EXISTS.getCode(),CommonStatusEnum.DRIVER_CAR_BIND_EXISTS.getValue());
        }
        //如果车辆已经被绑定，不再绑定
        queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("car_id",driverCarBindingRelationship.getCarId());
        queryWrapper.eq("bind_state", DriverCarConstans.DRIVER_CAR_BIND);
        Integer carCount = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if(carCount.intValue()>0){
           return ResponseResult.fail(CommonStatusEnum.CAR_BIND_EXISTS.getCode(),CommonStatusEnum.CAR_BIND_EXISTS.getValue());
        }
        //如果司机已经绑定，不再绑定
        queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("driver_id",driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("bind_state", DriverCarConstans.DRIVER_CAR_BIND);
        Integer driverCount = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if(driverCount.intValue()>0){
            return  ResponseResult.fail(CommonStatusEnum.DRIVER_BIND_EXISTS.getCode(),CommonStatusEnum.DRIVER_BIND_EXISTS.getValue());
        }

        LocalDateTime now = LocalDateTime.now();
        driverCarBindingRelationship.setBindingTime(now);
        driverCarBindingRelationship.setBindState(DriverCarConstans.DRIVER_CAR_BIND);
        driverCarBindingRelationshipMapper.insert(driverCarBindingRelationship);
        return ResponseResult.success("");
    }

    /**
     * 司机车辆解绑
     * @param driverCarBindingRelationship
     * @return
     */
    @Override
    public ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationship) {
        LocalDateTime now = LocalDateTime.now();
        //查询车辆司机绑定关系
        Map<String, Object> map = new HashMap<>();
        map.put("driver_id",driverCarBindingRelationship.getDriverId());
        map.put("car_id",driverCarBindingRelationship.getCarId());
        map.put("bind_state",DriverCarConstans.DRIVER_CAR_BIND);
        List<DriverCarBindingRelationship> driveCarlist = driverCarBindingRelationshipMapper.selectByMap(map);
        //如果为空绑定关系不存在
        if(driveCarlist.isEmpty()){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS.getCode(),CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS.getValue());
        }
        DriverCarBindingRelationship driverCar = driveCarlist.get(0);
        //修改绑定关系为解绑
        driverCar.setBindState(DriverCarConstans.DRIVER_CAR_UNBIND);
        driverCar.setUnBindingTime(now);
        driverCarBindingRelationshipMapper.updateById(driverCar);
        return ResponseResult.success("");
    }


}
