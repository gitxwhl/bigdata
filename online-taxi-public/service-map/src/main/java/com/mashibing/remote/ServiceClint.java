package com.mashibing.remote;

import com.mashibing.internalcommon.constent.AmapConfigConstents;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.ServiceRespose;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class ServiceClint {

    @Value("${key.amap}")
    private String amapkey;
    @Autowired
    private RestTemplate restTemplate;


    public ResponseResult add(String name){
        //组装请求url
        StringBuilder builder=new StringBuilder();
        builder.append(AmapConfigConstents.SERVICE_URL);
        builder.append("?");
        builder.append("key=" + amapkey);
        builder.append("&");
        builder.append("name=" + name);
        ResponseEntity<String> forEntity = restTemplate.postForEntity(builder.toString(), null, String.class);
        String body = forEntity.getBody();
        JSONObject result = JSONObject.fromObject(body);
        JSONObject data = result.getJSONObject("data");
        String sid = data.getString("sid");
        ServiceRespose serviceRespose=new ServiceRespose();
        serviceRespose.setSid(sid);
        return ResponseResult.success(serviceRespose);
    }
}
