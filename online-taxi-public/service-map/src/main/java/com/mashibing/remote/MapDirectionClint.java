package com.mashibing.remote;

import com.mashibing.internalcommon.constent.AmapConfigConstents;
import com.mashibing.internalcommon.dto.ForecastPriceDto;
import com.mashibing.internalcommon.response.DirectionResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 调用地图服务获取距离和时长
 */
@Service
@Slf4j
public class MapDirectionClint {
    @Value("${key.amap}")
    private String amapkey;
    @Autowired
    private RestTemplate restTemplate;

    public DirectionResponse diretction(ForecastPriceDto forecastPriceDto){
        //组装请求url
        StringBuilder builder=new StringBuilder();
        builder.append(AmapConfigConstents.DIRECTION_URL);
        builder.append("?");
        builder.append("origin="+forecastPriceDto.getDepLongitude());
        builder.append(",");
        builder.append(forecastPriceDto.getDeplatitude());
        builder.append("&");
        builder.append("destination=" + forecastPriceDto.getDestLongitude());
        builder.append(",");
        builder.append(forecastPriceDto.getDestlatitude());
        builder.append("&");
        builder.append("extensions=base");
        builder.append("&");
        builder.append("output=json");
        builder.append("&");
        builder.append("key=" + amapkey);
        System.out.println(builder.toString());
//        调用高德接口
        ResponseEntity<String> driectionEntity = restTemplate.getForEntity(builder.toString(), String.class);
        log.info("高德地图路径规划，返回信息" + driectionEntity.getBody());
         String driectionGetBody = driectionEntity.getBody();
        //解析接口
        DirectionResponse directionResponse = parseDriectionEntity(driectionGetBody);
        return directionResponse;
    }
    public DirectionResponse   parseDriectionEntity(String driectionGetBody){
        DirectionResponse directionResponse=null;
        try{

            //将字符串转化为jison的对象
            //最外层
            JSONObject result = JSONObject.fromObject(driectionGetBody);
            //获取成功或者失败的状态值1代表成功0代表失败
            int status = result.getInt(AmapConfigConstents.STATUS);
            if(result.has(AmapConfigConstents.STATUS)){
                if(status==1){
                    if(result.has(AmapConfigConstents.ROUTE)){
                       JSONObject routeObject = result.getJSONObject(AmapConfigConstents.ROUTE);
                         JSONArray pathsArray = routeObject.getJSONArray(AmapConfigConstents.PATHS);
                         JSONObject pathObject = pathsArray.getJSONObject(0);
                        directionResponse = new DirectionResponse();
                         if(pathObject.has(AmapConfigConstents.DISTANCE)){
                              int distance = pathObject.getInt(AmapConfigConstents.DISTANCE);
                             directionResponse.setDistance(distance);
                         }
                         if(pathObject.has(AmapConfigConstents.DURATION)){
                            int duration = pathObject.getInt(AmapConfigConstents.DURATION);
                            directionResponse.setDuration(duration);
                         }

                    }
                }
            }



        }catch (Exception e){

        }



        return directionResponse;
    }





}
