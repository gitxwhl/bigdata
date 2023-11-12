package com.mstdemo.mst.service;

import com.mstdemo.mst.bean.DeptInfo;
import com.mstdemo.mst.bean.Permission;

import java.util.List;

public interface DeptInfoService {
    /**
     * 测试返回主键id
     *
     * @param deptInfo
     * @return
     */
    int addDept(DeptInfo deptInfo);

    /**
     * 递归调用
     *
     * @return
     */
    List<Permission> findPermission();

    /**
     * sftp上传xls
     */
    void exl();

    /**
     * sftp上传   xlsx
     */
    void exlxlsx();

    /**
     *
     */
    void fpexl();
    /**
     * 入参不是用对象绑定，验证非空
     */
    List<DeptInfo> getDeptInfoById(String deptName);

}
