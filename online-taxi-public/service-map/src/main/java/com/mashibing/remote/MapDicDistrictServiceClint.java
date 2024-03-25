package com.mashibing.remote;

import com.mashibing.internalcommon.constent.AmapConfigConstents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 地图字典
 */
@Service
public class MapDicDistrictServiceClint {
    @Autowired
  private RestTemplate restTemplate;

    @Value("${key.amap}")
    private String key;
    /**
     * 拼接地区字典url
     * @return
     */
    public String initDistrict(String keywords) {
        //<用户的key>
        StringBuilder districtUrl=new StringBuilder();
        districtUrl.append(AmapConfigConstents.DISTRICT_URL);
        districtUrl.append("?");
        districtUrl.append("keywords=" + keywords);
        districtUrl.append("&");
        districtUrl.append("subdistrict=3");
        districtUrl.append("&");
        districtUrl.append("key=" + key);
        ResponseEntity<String> forEntity = restTemplate.getForEntity(districtUrl.toString(), String.class);
        return forEntity.getBody();
    }




}
