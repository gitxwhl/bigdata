package com.atguigu.auth.service;


import atguigu.model.system.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author dell
* @description 针对表【sys_menu(菜单表)】的数据库操作Service
* @createDate 2024-06-23 15:26:41
*/
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> findNodes();

    void removeMenuById(Long id);
}
