package com.mstdemo.mst.mapper;

import com.mstdemo.mst.bean.DeptInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeptInfoMapper {
    int addDept(DeptInfo deptInfo);
}
