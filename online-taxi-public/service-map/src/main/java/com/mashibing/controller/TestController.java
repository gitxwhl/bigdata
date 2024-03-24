package com.mashibing.controller;

import com.mashibing.internalcommon.dto.DicDistrict;
import com.mashibing.mapper.DicDistrictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class TestController {
    @Autowired
    private DicDistrictMapper dicDistrictMapper;
    
    
    
    @RequestMapping("/service-map-test")
    public String find(){
        return "service-map";
    }

    @RequestMapping("/baseMapper")
    public String baseMapper(){
        HashMap<String,Object> hashBiMap = new HashMap();
        hashBiMap.put("address_code","10100");
        dicDistrictMapper.selectByMap(hashBiMap);
        return "service-map";
    }



}
