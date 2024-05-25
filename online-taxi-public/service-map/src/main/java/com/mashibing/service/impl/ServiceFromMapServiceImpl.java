package com.mashibing.service.impl;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.remote.ServiceClint;
import com.mashibing.service.ServiceFromMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceFromMapServiceImpl implements ServiceFromMapService {
    @Autowired
    private ServiceClint serviceClint;

    @Override
    public ResponseResult add(String name) {
        return serviceClint.add(name);
    }
}
