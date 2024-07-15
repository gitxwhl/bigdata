package com.atguigu.auth.service.impl;

import atguigu.model.system.SysUser;
import com.atguigu.auth.mapper.SysUserMapper;
import com.atguigu.auth.service.SysUserService;
import com.atguigu.common.result.Result;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author
 * @since 2024-06-02
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    //更新用户状态
    @Override
    public Result updateStatus(Long id, Integer status) {
        //根据用户id查询用户对象
        SysUser user = this.getById(id);
        if(user.getStatus() != 0 && user.getStatus() != 1){
          return   Result.ok("数据不合法");
        }
        //设置修改状态
        user.setStatus(status);
        //调用方法实现修改
        return Result.ok(this.updateById(user));
    }

    //根据用户名查询用户信息
    @Override
    public SysUser getByUsername(String username) {
        LambdaUpdateWrapper<SysUser> objectLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        objectLambdaUpdateWrapper.eq(SysUser::getUsername,username);
        SysUser sysUser = this.getOne(objectLambdaUpdateWrapper);
        return sysUser;
    }
}
