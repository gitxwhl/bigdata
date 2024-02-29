package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.service.ForecastPriceService;
import com.mashibing.internalcommon.dto.ForecastPriceDto;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ForecastPrice")
public class ForecastPriceController {
        @Autowired
        private ForecastPriceService forecastPriceService;
        /**
         * 根据经纬度查询预估价格
         * @param forecastPriceDto
         * @return
         */
        @RequestMapping("/forecastPriceDto")
        public ResponseResult getforecastPrice(@RequestBody ForecastPriceDto forecastPriceDto){
                return forecastPriceService.getforecastPrice(forecastPriceDto);
        }






}
