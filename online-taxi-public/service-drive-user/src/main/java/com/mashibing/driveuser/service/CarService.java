package com.mashibing.driveuser.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;

public interface CarService extends IService<Car> {
    ResponseResult addCar(Car car);
}
