package com.canteen.service.impl;

import com.canteen.entity.StFileName;
import com.canteen.mapper.StFileNameMapper;
import com.canteen.service.StFileNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StFileNameServiceImpl implements StFileNameService {

    @Autowired
    private StFileNameMapper stFileNameMapper;

    @Override
    public void instName(String fileName) {
        stFileNameMapper.instName(fileName);

    }

    @Override
    public List<String> selsctName() {
        return stFileNameMapper.selsctName();
    }
}
