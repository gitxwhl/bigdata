package com.mstdemo.mst.service.impl;

import com.mstdemo.mst.bean.DeptInfo;
import com.mstdemo.mst.bean.Permission;
import com.mstdemo.mst.mapper.DeptInfoMapper;
import com.mstdemo.mst.service.DeptInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DeptInfoServiceImpl implements DeptInfoService {
    @Autowired
    private DeptInfoMapper deptInfoMapper;

    /**
     * 返回添加返回id
     * @param deptInfo
     * @return
     */
    @Override
    public int addDept(DeptInfo deptInfo) {
        int fla = deptInfoMapper.addDept(deptInfo);
        Integer id = deptInfo.getDeptId();
        return fla;
    }


    /**
     * 递归调用
     * @return
     */
    @Override
    public List<Permission> findPermission(){
        Permission parent = new Permission();
        parent.setId("0");
        queryChildPermissions(parent);
        return parent.getChildren();
    }
    /**
     *递归
     * 自己调用自己
     * 方法一定要存在跳出逻辑
     * 方法调用时，参数之间应该有规律
     * @param parent
     * @return
     */
    public void queryChildPermissions(Permission parent){
        List<Permission> childPermissions = deptInfoMapper.findPermission(parent.getId());
        for (Permission permission:childPermissions){
            queryChildPermissions(permission);
        }
        parent.setChildren(childPermissions);
    }
}
