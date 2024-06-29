package com.atguigu.auth.utils;

import atguigu.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuHelper {

 //递归方法创建菜单
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {
        //封装最终树形结构
        List<SysMenu> trees = new ArrayList<>();
        //把所有的菜单数据进行遍历
        for(SysMenu sysMenu : sysMenuList){
            //递归的入口
            if(sysMenu.getParentId().longValue()==0){
                trees.add(getChildrens(sysMenu,sysMenuList));
            }
        }
        return trees;
    }

    //递归遍历所有菜单，封装到树形结构中
    public static SysMenu getChildrens(SysMenu sysMenu,List<SysMenu> sysMenuList){
        sysMenu.setChildren(new ArrayList<SysMenu>());
        for (SysMenu menu : sysMenuList){
            //如果当前菜单id等于子菜单的父id，则将子菜单封装到当前菜单的children集合中
            if(sysMenu.getId().longValue()==menu.getParentId().longValue()){
                if(sysMenu.getChildren()==null){
                    sysMenu.setChildren(new ArrayList<>());
                }
                sysMenu.getChildren().add(getChildrens(menu,sysMenuList));
            }
        }
        return sysMenu;
    }



}
