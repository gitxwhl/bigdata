package com.mashibing.driveuser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashibing.driveuser.mapper.CarMapper;
import com.mashibing.driveuser.service.ICarService;
import com.mashibing.internalcommon.dto.Car;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 晁鹏飞
 * @since 2024-04-15
 */
@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements ICarService {

}
