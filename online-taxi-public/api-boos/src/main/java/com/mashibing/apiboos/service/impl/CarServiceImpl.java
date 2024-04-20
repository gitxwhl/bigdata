package com.mashibing.apiboos.service.impl;
import com.mashibing.apiboos.remote.DriveUserClint;
import com.mashibing.apiboos.service.CarService;
import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private DriveUserClint driveUserClint;

    //添加车辆
    @Override
    public ResponseResult addCar(Car car) {
        return driveUserClint.addCar(car);
    }
}
