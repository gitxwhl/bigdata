package com.mashibing.service;

import com.mashibing.internalcommon.dto.ForecastPriceDto;
import com.mashibing.internalcommon.dto.ResponseResult;

public interface DirectionService {

    /**
     * 根据起点经纬度和终点经纬度获取距离（米）和时长（分种）
     * @return
     */
     ResponseResult driving(ForecastPriceDto forecastPriceDto);

}
