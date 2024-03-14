package com.mashibing.controller;

import com.mashibing.internalcommon.dto.ForecastPriceDto;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.service.ForecastPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("forecast-price")
public class ForecastPriceController {
    @Autowired
   private ForecastPriceService forecastPriceService;

    /**
     * 根据经纬度获取价格
     * @param forecastPriceDto
     * @return
     */
    @RequestMapping("findForecastPrice")
    public ResponseResult FindForecastPrice(@RequestBody ForecastPriceDto forecastPriceDto){
        return forecastPriceService.ForecastPrice(forecastPriceDto);
    }

}
