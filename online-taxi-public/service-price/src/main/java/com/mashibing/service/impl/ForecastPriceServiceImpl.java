package com.mashibing.service.impl;

import com.mashibing.internalcommon.dto.ForecastPriceDto;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.ForecastPriceResponse;
import com.mashibing.service.ForecastPriceService;
import org.springframework.stereotype.Service;

@Service
public class ForecastPriceServiceImpl implements ForecastPriceService {

    /**
     * 根据经纬度获取价格
     * @param forecastPriceDto
     * @return
     */
    @Override
    public ResponseResult ForecastPrice(ForecastPriceDto forecastPriceDto) {
        //起点经度
        String depLongitude  = forecastPriceDto.getDepLongitude();
        //起点纬度
        String deplatitude=forecastPriceDto.getDeplatitude();
        //终点经度
        String destLongitude = forecastPriceDto.getDestLongitude();
        //终点纬度
        String destlatitude =forecastPriceDto.getDestlatitude();
        //根据经纬度调用地图服务,查询距离和时长

        //调用计价规则

        //根据距离和时长和计价规则，计算价格

        return ResponseResult.success();

    }
}
