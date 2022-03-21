package com.property.service.impl;

import com.property.entity.Trepaircheck;
import com.property.mapper.TrepaircheckMapper;
import com.property.service.TrepaircheckService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrepaircheckServiceimpl implements TrepaircheckService {

    @Autowired
   private TrepaircheckMapper TrepaircheckMapper;

    @Override
    public Trepaircheck getTrepaircheckMapper() {
        return TrepaircheckMapper.getTrepaircheckMapper();
    }
}