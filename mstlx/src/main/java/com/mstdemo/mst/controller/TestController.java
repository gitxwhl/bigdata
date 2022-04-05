package com.mstdemo.mst.controller;
import com.mstdemo.mst.bean.DeptInfo;
import com.mstdemo.mst.bean.Permission;
import com.mstdemo.mst.service.DeptInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private DeptInfoService deptInfoService;
    /**
     * 测试返回主键id
     * @param deptInfo
     * @return
     */
    @RequestMapping("/add")
    public int add(@RequestBody DeptInfo deptInfo) {
        return deptInfoService.addDept(deptInfo);
    }
    /**
     * 递归调用
     * @return
     */
    @RequestMapping("/findPermission")
    public List<Permission> findPermission(){
        List<Permission> permissions = deptInfoService.findPermission();
        return permissions;
    }

}
