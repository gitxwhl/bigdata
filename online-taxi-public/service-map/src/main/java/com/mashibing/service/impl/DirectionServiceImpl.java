package com.mashibing.service.impl;

import com.mashibing.internalcommon.dto.ForecastPriceDto;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DirectionResponse;
import com.mashibing.remote.MapDirectionClint;
import com.mashibing.service.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectionServiceImpl implements DirectionService {
    @Autowired
   private MapDirectionClint mapDirectionClint;

    /**
     * 根据起点经纬度和终点经纬度获取距离（米）和时长（分种）
     * @return
     */
    @Override
    public ResponseResult driving(ForecastPriceDto forecastPriceDto) {
        //根据经纬度调用地图服务
        DirectionResponse directionResponse = mapDirectionClint.diretction(forecastPriceDto);
        return ResponseResult.success(directionResponse);
    }
}
