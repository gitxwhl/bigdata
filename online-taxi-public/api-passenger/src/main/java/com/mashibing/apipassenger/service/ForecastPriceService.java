package com.mashibing.apipassenger.service;

import com.mashibing.internalcommon.dto.ForecastPriceDto;
import com.mashibing.internalcommon.dto.ResponseResult;

public interface ForecastPriceService {




    ResponseResult getforecastPrice(ForecastPriceDto forecastPriceDto);

}
