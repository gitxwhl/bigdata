package com.mstdemo.mst.service.impl;

import com.mstdemo.mst.bean.DeptInfo;
import com.mstdemo.mst.mapper.DeptInfoMapper;
import com.mstdemo.mst.service.DeptInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeptInfoServiceImpl implements DeptInfoService {
    @Autowired
   private DeptInfoMapper deptInfoMapper;


    @Override
    public int addDept(DeptInfo deptInfo) {
        int fla = deptInfoMapper.addDept(deptInfo);
       Integer id = deptInfo.getDeptId();
        return fla;
    }
}
