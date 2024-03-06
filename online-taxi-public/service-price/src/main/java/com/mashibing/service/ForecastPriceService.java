package com.mashibing.service;

import com.mashibing.internalcommon.dto.ForecastPriceDto;
import com.mashibing.internalcommon.dto.ResponseResult;

public interface ForecastPriceService {

    //根据经纬度获取价格
     ResponseResult ForecastPrice(ForecastPriceDto forecastPriceDto);
}
