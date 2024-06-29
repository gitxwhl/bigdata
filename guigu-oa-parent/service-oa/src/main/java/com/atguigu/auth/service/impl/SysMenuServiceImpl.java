package com.atguigu.auth.service.impl;

import atguigu.model.system.SysMenu;
import com.atguigu.auth.service.SysMenuService;
import com.atguigu.auth.mapper.SysMenuMapper;
import com.atguigu.auth.utils.MenuHelper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author dell
* @description 针对表【sys_menu(菜单表)】的数据库操作Service实现
* @createDate 2024-06-23 15:26:41
*/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements SysMenuService{

    @Override
    public List<SysMenu> findNodes() {
        //查询所有菜单数据 封装成树形结构  方式一   递归   不建议用 每一条都需要查看数据库性能低
//        List<SysMenu> sysMenuList = this.list();
//        List<SysMenu> sysMenus = MenuHelper.buildTree(sysMenuList);
//        return sysMenus;

        List<SysMenu> sysMenuList = this.list();
        //方式二：递归查询所有菜单数据
//        for(SysMenu sysMenu :sysMenuList){
//            if(sysMenu.getParentId()==0){
//                //递归入口，需要在集合里添加子集，子集获取需要递归
//                if(sysMenu.getChildren()==null){
//                    sysMenu.setChildren(new ArrayList<>());
//                }
//                sysMenu.getChildren().add(getSysMenuClilds(sysMenu));
//            }
//        }
//        return sysMenuList;

        //方式三：双层for循环  思想：将每一层菜单都看成是子菜单   需要遍历所有数据取值，效率不高
//        List<SysMenu> sysMenuLists = new ArrayList<>();
//        for(SysMenu sysMenu : sysMenuList){
//            SysMenu child = sysMenu;
//            if(sysMenu.getParentId()==0){
//                sysMenuLists.add(sysMenu);
//            }else {
//                for(SysMenu menu :sysMenuList){
//                    if(child.getParentId()==menu.getId()){
//                        //父节点
//                        SysMenu parent= menu;
//                        if(parent.getChildren()==null){
//                            parent.setChildren(new ArrayList<>());
//                        }
//                        //组合关系
//                        parent.getChildren().add(child);
////                        break;
//                    }
//                }
//            }
//        }
//        return sysMenuLists;

        //方式四  通过map方式   通过map索引方式，不需要要遍历所有的数据效率高
        List<SysMenu> sysMenuEndList = new ArrayList<>();
        Map<Long,SysMenu> menuMap = new HashMap<>();
//        菜单id和菜单值关联
        for (SysMenu sysMenu :sysMenuList){
            menuMap.put(sysMenu.getId(),sysMenu);
        }
//        将每一个菜单都看成是子菜单
        for(SysMenu sysMenu: sysMenuList){
            SysMenu child = sysMenu;
            if(sysMenu.getParentId()==0){
                //将顶节点添加到集合中
                sysMenuEndList.add(sysMenu);
            }else{
                //不是顶节点
                //通过父id获取子菜单
                SysMenu parent = menuMap.get(child.getParentId());
                //组合父子关系
                if(parent.getChildren()==null){
                            parent.setChildren(new ArrayList<>());
                        }
                parent.getChildren().add(child);

            }
        }
        return sysMenuEndList;
    }

    //递归
//   public SysMenu getSysMenuClilds(SysMenu parent){
//        //根据上级id获取下级
//       LambdaUpdateWrapper<SysMenu> wrapper = new LambdaUpdateWrapper<>();
//       wrapper.eq(SysMenu::getParentId,parent.getId());
//       List<SysMenu> menuList = this.list(wrapper);
//       //子下面有子菜单
//       for(SysMenu sysMenu :menuList){
//           getSysMenuClilds(sysMenu);
//       }
//       //组合子父关系
//       parent.setChildren(menuList);
//       return parent;
//    }


}




