package com.mashibing.service.impl;
import com.mashibing.internalcommon.constent.CommonStatusEnum;
import com.mashibing.internalcommon.dto.ForecastPriceDto;
import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DirectionResponse;
import com.mashibing.internalcommon.response.ForecastPriceResponse;
import com.mashibing.mapper.PriceRuleMapper;
import com.mashibing.remote.ServiceMapClint;
import com.mashibing.service.ForecastPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
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
    public ResponseResult<ForecastPriceResponse> ForecastPrice(ForecastPriceDto forecastPriceDto) {
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
        priceRuleMap.put("city_code","10000");
        priceRuleMap.put("vehicle_type","11");
        List<PriceRule> priceRulesList = priceRuleMapper.selectByMap(priceRuleMap);
        if(priceRulesList.size()==0){
            ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(),CommonStatusEnum.PRICE_RULE_EMPTY.getValue());
        }
        PriceRule priceRule = priceRulesList.get(0);
        //根据距离和时长和计价规则，计算价格
        Double  forecastPrice = getPrice(distance,duration,priceRule);

        return ResponseResult.success(forecastPrice);
    }


    /**
     * 根据距离时长，计价规则计算最终价格
     * @param distance 距离
     * @param duration 时长
     * @param priceRule 计价规则
     * @return
     */
    public static Double getPrice(Integer distance,Integer duration, PriceRule priceRule){
        BigDecimal price =new BigDecimal(0);
        //起步价
        Double startPrice = priceRule.getStartFare();
        BigDecimal startPriceDecimal =new BigDecimal(startPrice);
        price = price.add(startPriceDecimal);
        //里程费
        //m
        BigDecimal  distancemile = new BigDecimal(distance);
        //m  到km转换  总里程km
        BigDecimal distancemileBigDecimal= distancemile.divide(new BigDecimal(1000),2,BigDecimal.ROUND_HALF_UP);
        //起步里程
        Integer startMile = priceRule.getStartMile();
         BigDecimal startMileBigDecimal = new  BigDecimal(startMile);
         //里程差值
        double distanceSubtract = distancemileBigDecimal.subtract(startMileBigDecimal).doubleValue();
        //        最终收费的里程数 km
         Double mile =distanceSubtract < 0 ? 0:distanceSubtract;
         BigDecimal mileBigDecimal = new BigDecimal(mile);
         //获取单价
        Double unitPricePerMile = priceRule.getUnitPricePerMile();
        BigDecimal unitPricePerMileBigDecimal   = new BigDecimal(unitPricePerMile);
        //里程价
        BigDecimal  lcprice= mileBigDecimal.multiply(unitPricePerMileBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP));
        price = price.add(lcprice);
        //时长费
        //时长单价
        Double unitPricePerMinute = priceRule.getUnitPricePerMinute();
        BigDecimal unitPricePerMinuteBigDecimal=new BigDecimal(unitPricePerMinute);
        //时长分钟数
         BigDecimal durationBigDecimal = new BigDecimal(duration);
        BigDecimal miue = durationBigDecimal.divide(new BigDecimal(60), 2, BigDecimal.ROUND_HALF_UP);

        //时长总费用
        BigDecimal timeFire = miue.multiply(unitPricePerMinuteBigDecimal);
        Double priceAll = price.add(timeFire).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        return priceAll ;
    }

   /* public static void main(String[] args) {
        PriceRule priceRule=new PriceRule();
        priceRule.setUnitPricePerMile(1.8);
        priceRule.setUnitPricePerMinute(0.5);
        priceRule.setStartFare(10.0);
        priceRule.setStartMile(3);
        System.out.println(getPrice(6500,1800,priceRule));
    }*/
}
