package com.mashibing.remote;

import com.mashibing.internalcommon.dto.ForecastPriceDto;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DirectionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-map")
public interface ServiceMapClint {
    @RequestMapping(method=RequestMethod.GET,value = "/service-map/driving")
     ResponseResult<DirectionResponse> driving(@RequestBody ForecastPriceDto forecastPriceDto);

}
