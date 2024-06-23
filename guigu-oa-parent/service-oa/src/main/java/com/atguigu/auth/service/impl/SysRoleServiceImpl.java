package com.atguigu.auth.service.impl;

import atguigu.model.system.SysRole;
import atguigu.model.system.SysUserRole;
import atguigu.vo.system.AssginRoleVo;
import com.atguigu.auth.mapper.SysRoleMapper;
import com.atguigu.auth.service.SysRoleService;
import com.atguigu.auth.service.SysUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public Map<String, Object> getRolesByUserId(Long userId) {
      //查询所有的角色，返回list集合
        List<SysRole> listAllRole = this.list();
        //根据用户id到用户角色关系表中查询所有角色id
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId,userId);
        List<SysUserRole> sysUserRoles = sysUserRoleService.list(wrapper);
        //从查询出来的用户id对应的角色id进行封装
        List<Long> roleIds = sysUserRoles.stream().map(sysUserRole -> sysUserRole.getRoleId()).collect(Collectors.toList());
        //根据查询所有的角色id，找到对应信息
        //根据角色id到所有角色list集合中比较
        List<SysRole> sysRoleList = new ArrayList<>();
        listAllRole.forEach(sysRole -> {
            if(roleIds.contains(sysRole.getId())) {
                sysRoleList.add(sysRole);
            }else {
            }
        });

        //把得到的两部分数据封装到map集合中
        Map<String, Object> map = new HashMap<>();
        //所有角色list集合
        map.put("listAllRole",listAllRole);
        //当前用户角色list集合
        map.put("sysRoleList",sysRoleList);
        return map;
    }




    //给用户分配角色
    @Override
    public void doAssginRoleVo(AssginRoleVo assginRoleVo) {
        //根据用户id删除之前分配的角色数据，用户角色关系表的数据通过用户id进行删除
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId,assginRoleVo.getUserId());
        sysUserRoleService.remove(wrapper);

        //重新进行分配 遍历所有角色id，进行角色分配(前端选择的角色id集合)
        List<Long> roleIdList = assginRoleVo.getRoleIdList();
        SysUserRole sysUserRole=new SysUserRole();
        for(Long roleId : roleIdList){
            sysUserRole.setUserId(assginRoleVo.getUserId());
            sysUserRole.setRoleId(roleId);
            sysUserRoleService.save(sysUserRole);
        }
    }
}
