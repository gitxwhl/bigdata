package com.atguigu.controller;


import atguigu.model.system.SysRole;
import com.atguigu.common.result.Result;
import com.atguigu.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Api(tags = "角色管理接口")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    // http://localhost:8800/admin/system/sysRole/findAll

    //注入service
    @Autowired
    private SysRoleService sysRoleService;

//    @ApiOperation("查询所有角色")
    @GetMapping("/findAll")
    public Result findAll() {
        //调用service的方法
        List<SysRole> list = sysRoleService.list();
        return   Result.ok(list);
    }



}
