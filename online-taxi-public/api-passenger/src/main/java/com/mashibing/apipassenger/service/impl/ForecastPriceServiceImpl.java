package com.mashibing.apipassenger.service.impl;

import com.mashibing.apipassenger.service.ForecastPriceService;
import com.mashibing.internalcommon.dto.ForecastPriceDto;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.ForecastPriceResponse;
import org.springframework.stereotype.Service;

@Service
public class ForecastPriceServiceImpl implements ForecastPriceService {
    /**
     * 根据经纬度查询预估价格
     * @param forecastPriceDto
     * @return
     */
    @Override
    public ResponseResult getforecastPrice(ForecastPriceDto forecastPriceDto) {
        //起点经度
        String depLongitude  = forecastPriceDto.getDepLongitude();
        //起点纬度
        String deplatitude=forecastPriceDto.getDeplatitude();
        //终点经度
        String destLongitude = forecastPriceDto.getDestLongitude();
        //终点纬度
         String destlatitude =forecastPriceDto.getDestlatitude();
         //根据经纬度返回价格信息
        ForecastPriceResponse forecastPriceResponse=new ForecastPriceResponse();
        forecastPriceResponse.setPrice(12.4);
        return ResponseResult.success(forecastPriceResponse);
    }
}
