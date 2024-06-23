package com.atguigu.auth.controller;


import atguigu.model.system.SysRole;
import atguigu.vo.system.AssginRoleVo;
import atguigu.vo.system.SysRoleQueryVo;
import com.atguigu.auth.service.SysRoleService;
import com.atguigu.common.config.exception.GuiguException;
import com.atguigu.common.result.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @ApiOperation("service 分页 条件角色查询")
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
//        pageModel.getRecords()
        return Result.ok(pageModel);
    }



    @ApiOperation("添加角色")
    @PostMapping ("save")
    public Result save(@RequestBody SysRole role){
        //调用service方法
//        try {
//        int i= 10/0;
//        }catch (Exception e){
//            throw new GuiguException(2001,"出现自定义异常");
//        }
        boolean is_success = sysRoleService.save(role);
        if(is_success){
           return Result.ok(is_success);
        }else {
           return Result.fail(is_success);
        }
    }
    @ApiOperation("修改角色")
    @PutMapping ("update")
    public Result update(@RequestBody SysRole role){
        boolean is_success = sysRoleService.updateById(role);
        if(is_success){
            return Result.ok(is_success);
        }else {
            return Result.fail(is_success);
        }
    }
    @ApiOperation("查询角色-根据id查询")
    @GetMapping("get/{id}")
    public Result updateById(@PathVariable("id") Long id){
        SysRole sysRole = sysRoleService.getById(id);
        return Result.ok(sysRole);
    }
    @ApiOperation("删除角色")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable("id")Long id){
        boolean is_success = sysRoleService.removeById(id);
        if(is_success){
            return Result.ok(is_success);
        }else {
            return Result.fail(is_success);
        }
    }
    @ApiOperation("批量删除角色")
    @PostMapping("batchRemove")
    // 前端数组 [1,2,3]
    public Result batchRemove(@RequestBody List<Long> idList){
        boolean is_success = sysRoleService.removeByIds(idList);
        if(is_success){
            return Result.ok(is_success);
        }else {
            return Result.fail(is_success);
        }
    }


    @ApiOperation("根据用户id获取用户当前角色，获取所有角色")
    @GetMapping("toAssign/{userId}")
    public Result getRolesByUserId(@PathVariable Long userId){
        Map<String,Object> map = sysRoleService.getRolesByUserId(userId);
        return Result.ok(map);
    }

    @ApiOperation("分配用户角色")
    @PostMapping("/doAssign")
    public Result doAssginRoleVo(@RequestBody AssginRoleVo assginRoleVo){
        sysRoleService.doAssginRoleVo(assginRoleVo);
        return Result.ok();
    }


}
