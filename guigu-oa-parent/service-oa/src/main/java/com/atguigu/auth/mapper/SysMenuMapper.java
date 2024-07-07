package com.atguigu.auth.mapper;


import atguigu.model.system.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author dell
* @description 针对表【sys_menu(菜单表)】的数据库操作Mapper
* @createDate 2024-06-23 15:26:41
* @Entity com.atguigu.auth.domain.SysMenu
*/
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> findMenuListByUserId(@Param("userId")Long userId);
}




