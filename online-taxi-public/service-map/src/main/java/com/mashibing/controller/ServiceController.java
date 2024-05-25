package com.mashibing.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.service.ServiceFromMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
public class ServiceController {
    @Autowired
    private ServiceFromMapService serviceFromMapService;

    /**
     * 添加服务
     *
     * @return
     */
    @RequestMapping("/add")
    public ResponseResult add(String name) {
        return serviceFromMapService.add(name);
    }

}
