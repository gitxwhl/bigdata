package com.property.service.impl;

import com.property.entity.WyVegetation;
import com.property.mapper.WyVegetationMapper;
import com.property.service.WyVegetationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WyVegetationServiceImpl implements WyVegetationService {

    @Autowired
    private  WyVegetationMapper wyVegetationMapper;
    /**
     * 新增植被
     * @param wyVegetation
     * @return
     */
    @Override
    public int gwtNewlyVegetation(WyVegetation wyVegetation) {
       int i = wyVegetationMapper.gwtNewlyVegetation(wyVegetation);
       return i;
    }
}
