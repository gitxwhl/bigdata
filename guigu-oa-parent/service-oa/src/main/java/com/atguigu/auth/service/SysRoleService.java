package com.atguigu.auth.service;

import atguigu.model.system.SysRole;
import atguigu.vo.system.AssginRoleVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface SysRoleService extends IService<SysRole> {


    Map<String, Object> getRolesByUserId(Long userId);

    void doAssginRoleVo(AssginRoleVo assginRoleVo);
}
