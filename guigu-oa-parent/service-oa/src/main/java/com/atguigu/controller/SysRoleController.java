package com.atguigu.controller;


import atguigu.model.system.SysRole;
import com.atguigu.common.result.Result;
import com.atguigu.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.atguigu.vo.system.SysRoleQueryVo;
@Api(tags = "角色管理接口")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    // http://localhost:8800/admin/system/sysRole/findAll

    //注入service
    @Autowired
    private SysRoleService sysRoleService;
    //条件分页查询
    //page 当前页  limit 每页显示条数
    @ApiOperation("查询所有角色")
    @GetMapping("/findAll")
    public Result findAll() {
        //调用service的方法
        List<SysRole> list = sysRoleService.list();
        return   Result.ok(list);
    }
    @GetMapping("{page}/{limit}")
    public Result pageQueryRole(@PathVariable Long page,
                                @PathVariable Long limit,
                                SysRoleQueryVo sysRoleQueryVo
                                ){
        //调用service实现

        //创建page对象，传分页相关参数
        Page<SysRole> pageParam = new Page<>(page,limit);

        //封装，判断条件是否为空，不为空进行封装
        String roleName = sysRoleQueryVo.getRoleName();
        LambdaQueryWrapper<SysRole> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(roleName)){
            lambdaQueryWrapper.like(SysRole::getRoleName, roleName);
        }
        //调用方法实现查询
        IPage<SysRole> pageModel = sysRoleService.page(pageParam, lambdaQueryWrapper);
        return Result.ok(pageModel);
    }



}
