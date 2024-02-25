package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.service.TookenService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TookenController {
    @Autowired
    private TookenService tookenService;

    /**
     * 根据刷新tooken获取tooken
     * @param tokenResponse
     * @return
     */
    @RequestMapping("/refresh-tooken")
    public ResponseResult refreshTooken(@RequestBody TokenResponse tokenResponse){
        String refreshToken = tokenResponse.getRefreshToken();
        System.out.println("原来的refreshTooken"+refreshToken);
        return tookenService.refreshTooken(refreshToken);

    }

}
