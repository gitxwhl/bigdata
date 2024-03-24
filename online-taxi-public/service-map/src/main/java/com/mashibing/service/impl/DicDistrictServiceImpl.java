package com.mashibing.service.impl;

import com.mashibing.internalcommon.constent.AmapConfigConstents;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.service.DicDistrictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DicDistrictServiceImpl implements DicDistrictService
{
    @Value("${key.amap}")
    private String key;
    /**
     * 拼接地区字典url
     * @return
     */
    @Override
    public ResponseResult initDistrict(String keywords) {
    //<用户的key>
    StringBuilder districtUrl=new StringBuilder();
    districtUrl.append(AmapConfigConstents.DISTRICT_URL);
    districtUrl.append("?");
    districtUrl.append("keywords=" + keywords);
    districtUrl.append("&");
    districtUrl.append("subdistrict=3");
    districtUrl.append("&");
    districtUrl.append("key=" + key);
    log.info("地区url"+ districtUrl.toString());
        return ResponseResult.success(districtUrl.toString());
    }
}
