package com.atguigu.auth.controller;


import atguigu.model.system.SysUser;
import atguigu.vo.system.SysUserQueryVo;
import com.atguigu.auth.service.SysUserService;
import com.atguigu.common.result.Result;
import com.atguigu.common.utils.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author
 * @since 2024-06-02
 */
@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService service;

        //用户条件分页查询
    @ApiOperation("用户条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable("page") Long page,
                        @PathVariable("limit") Long limit,
                        SysUserQueryVo sysUserQueryVo
                        ){
        //创建page对象
        Page<SysUser> pageparam = new Page<>(page,limit);
        //封装条件，判断条件值不为空
        String keyword = sysUserQueryVo.getKeyword();
        String createTimeBegin = sysUserQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysUserQueryVo.getCreateTimeEnd();
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper= new LambdaQueryWrapper<>();
        //判断条件值不为空
        //like 模糊查询
        if(!StringUtils.isEmpty(keyword)){
            lambdaQueryWrapper.like(SysUser::getName, keyword);
        }
        //ge 大于等于
        if(!StringUtils.isEmpty(createTimeBegin)){
            lambdaQueryWrapper.ge(SysUser::getCreateTime, createTimeBegin);
        }
        //le 小于等于
        if(!StringUtils.isEmpty(createTimeEnd)){
            lambdaQueryWrapper.le(SysUser::getUpdateTime, createTimeEnd);
        }

        //调用mp的方法实现条件分页查询
        IPage<SysUser> sysUserPage = service.page(pageparam, lambdaQueryWrapper);
        return Result.ok(sysUserPage);
    }

    @ApiOperation(value = "获取用户")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        SysUser user = service.getById(id);
        return Result.ok(user);
    }

    @ApiOperation(value = "保存用户")
    @PostMapping("save")
    public Result save(@RequestBody SysUser user) {
        //添加用户密码md5加密
        String password = user.getPassword();
        String encrypt = MD5.encrypt(password);
        user.setPassword(encrypt);
        service.save(user);
        return Result.ok();
    }

    @ApiOperation(value = "更新用户")
    @PutMapping("update")
    public Result updateById(@RequestBody SysUser user) {
        service.updateById(user);
        return Result.ok();
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        service.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "更新状态")
    @GetMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        return Result.ok(service.updateStatus(id, status));
    }


}

