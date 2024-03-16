package com.mashibing.controller;

import com.mashibing.internalcommon.dto.ForecastPriceDto;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DirectionResponse;
import com.mashibing.service.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service-map")
public class DirectionController {
    @Autowired
    private DirectionService directionService;

    /**
     * 根据起点经纬度和终点经纬度获取距离（米）和时长（分种）
     * @return
     */
    @RequestMapping("/driving")
    public ResponseResult<DirectionResponse> driving(@RequestBody ForecastPriceDto forecastPriceDto){
        return directionService.driving(forecastPriceDto);
    }


}
