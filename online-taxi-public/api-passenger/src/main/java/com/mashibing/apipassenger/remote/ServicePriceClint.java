package com.mashibing.apipassenger.remote;

import com.mashibing.internalcommon.dto.ForecastPriceDto;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.ForecastPriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-price")
public interface ServicePriceClint {
    @PostMapping(value = "/forecast-price")
     ResponseResult<ForecastPriceResponse> FindForecastPrice(@RequestBody ForecastPriceDto forecastPriceDto);



}
