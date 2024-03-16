package com.mashibing.service.impl;

import com.mashibing.internalcommon.constent.CommonStatusEnum;
import com.mashibing.internalcommon.dto.ForecastPriceDto;
import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DirectionResponse;
import com.mashibing.mapper.PriceRuleMapper;
import com.mashibing.remote.ServiceMapClint;
import com.mashibing.service.ForecastPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ForecastPriceServiceImpl implements ForecastPriceService {
    @Autowired
    private ServiceMapClint serviceMapClint;
    @Autowired
    private PriceRuleMapper priceRuleMapper;
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
        ResponseResult<DirectionResponse> responseResult = serviceMapClint.driving(forecastPriceDto);
        int distance = responseResult.getData().getDistance();
        int duration = responseResult.getData().getDuration();
        log.info("距离"+distance);
        log.info("时长"+duration);
        //调用计价规则
       Map<String,Object> priceRuleMap = new HashMap<>();
        priceRuleMap.put("city_code","10");
        priceRuleMap.put("vehicle_type","11");
        List<PriceRule> priceRulesList = priceRuleMapper.selectByMap(priceRuleMap);
        if(priceRulesList.size()==0){
            ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(),CommonStatusEnum.PRICE_RULE_EMPTY.getValue());
        }
        PriceRule priceRule = priceRulesList.get(0);
        //根据距离和时长和计价规则，计算价格

        return ResponseResult.success(priceRule);

    }
}
