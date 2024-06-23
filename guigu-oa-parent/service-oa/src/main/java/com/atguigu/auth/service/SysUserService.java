package com.atguigu.auth.service;


import atguigu.model.system.SysUser;
import com.atguigu.common.result.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zpp
 * @since 2024-06-02
 */
public interface SysUserService extends IService<SysUser> {

    Result updateStatus(Long id, Integer status);
}
