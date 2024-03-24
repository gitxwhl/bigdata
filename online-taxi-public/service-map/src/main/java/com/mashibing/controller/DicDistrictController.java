package com.mashibing.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.service.DicDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DicDistrictController {

    @Autowired
    private DicDistrictService dicDistrictService;

    @RequestMapping("/initDistrict")
    public ResponseResult initDistrict(String keywords) {
        ResponseResult responseResult = dicDistrictService.initDistrict(keywords);
        return responseResult;
    }


}
