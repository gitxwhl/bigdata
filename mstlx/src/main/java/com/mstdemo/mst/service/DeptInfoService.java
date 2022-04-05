package com.mstdemo.mst.service;

import com.mstdemo.mst.bean.DeptInfo;
import com.mstdemo.mst.bean.Permission;

import java.util.List;

public interface DeptInfoService {
    /**
     * 测试返回主键id
     * @param deptInfo
     * @return
     */
    int addDept(DeptInfo deptInfo);

    /**
     * 递归调用
     * @return
     */
    public List<Permission> findPermission();


}
