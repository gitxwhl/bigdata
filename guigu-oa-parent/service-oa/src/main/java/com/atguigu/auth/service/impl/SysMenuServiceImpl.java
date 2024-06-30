package com.atguigu.auth.service.impl;

import atguigu.model.system.SysMenu;
import atguigu.model.system.SysRoleMenu;
import atguigu.vo.system.AssginMenuVo;
import com.atguigu.auth.service.SysMenuService;
import com.atguigu.auth.mapper.SysMenuMapper;
import com.atguigu.auth.service.SysRoleMenuService;
import com.atguigu.auth.utils.MenuHelper;
import com.atguigu.common.config.exception.GuiguException;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
* @author dell
* @description 针对表【sys_menu(菜单表)】的数据库操作Service实现
* @createDate 2024-06-23 15:26:41
*/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements SysMenuService{
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Override
    public List<SysMenu> findNodes() {
        //查询所有菜单数据 封装成树形结构  方式一   递归   不建议用 每一条都需要查看数据库性能低
        List<SysMenu> sysMenuList = this.list();
        List<SysMenu> sysMenus = MenuHelper.buildTree(sysMenuList);
        return sysMenus;

//        List<SysMenu> sysMenuList = this.list();
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
//        List<SysMenu> sysMenuEndList = new ArrayList<>();
//        Map<Long,SysMenu> menuMap = new HashMap<>();
////        菜单id和菜单值关联
//        for (SysMenu sysMenu :sysMenuList){
//            menuMap.put(sysMenu.getId(),sysMenu);
//        }
////        将每一个菜单都看成是子菜单
//        for(SysMenu sysMenu: sysMenuList){
//            SysMenu child = sysMenu;
//            if(sysMenu.getParentId()==0){
//                //将顶节点添加到集合中
//                sysMenuEndList.add(sysMenu);
//            }else{
//                //不是顶节点
//                //通过父id获取子菜单
//                SysMenu parent = menuMap.get(child.getParentId());
//                //组合父子关系
//                if(parent.getChildren()==null){
//                            parent.setChildren(new ArrayList<>());
//                        }
//                parent.getChildren().add(child);
//
//            }
//        }
//        return sysMenuEndList;
    }
    //删除菜单
    @Override
    public void removeMenuById(Long id) {
        //根据id查询如果有记录不删除，否则删除
        LambdaUpdateWrapper<SysMenu> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysMenu::getParentId,id);
        int count = this.count(wrapper);
        if(count>0){
            throw new GuiguException(201,"菜单下面有子菜单，不能删除");
        }
        this.removeById(id);
    }

    @Override
    public List<SysMenu> findMenuByRoleId(Long roleId) {
        //查询所有菜单，菜单状态为1
        LambdaUpdateWrapper<SysMenu> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysMenu::getStatus,1);
        List<SysMenu> sysMenuList = this.list(wrapper);
        //根据用户角色id查询，角色菜单关系表查询对应菜单id
        LambdaUpdateWrapper<SysRoleMenu> sysRoleMenuWrapper = new LambdaUpdateWrapper<>();
        sysRoleMenuWrapper.eq(SysRoleMenu::getRoleId,roleId);
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuService.list(sysRoleMenuWrapper);
        //获取角色菜单id集合
        List<Long> menuList = sysRoleMenuList.stream().map(c -> c.getMenuId()).collect(Collectors.toList());
        //当前角色分配菜单在所有菜单中存在，设置 isSelect=true  否则isSelect=false
        //根据获取菜单id，获取对应菜单对象
        //拿着菜单id，和所有菜单集合里面id进行比较，如果相同封装
        sysMenuList.stream().forEach(item->{
            if(menuList.contains(item.getId())){
                item.setSelect(true);
            }
            item.setSelect(false);
        });
        //返回菜单列表
        List<SysMenu> sysMenus = MenuHelper.buildTree(sysMenuList);
        return sysMenus;
    }

    @Override
    public void doAssign(AssginMenuVo assignMenuVo) {
        //根据角色id删除角色菜单关系表已经分配的菜单
        LambdaUpdateWrapper<SysRoleMenu> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysRoleMenu::getRoleId,assignMenuVo.getRoleId());
        sysRoleMenuService.remove(wrapper);
        //获取入参已选择的菜单id集合，重新分配
        List<Long> menuIdList = assignMenuVo.getMenuIdList();
        for (Long menuId :menuIdList){
            if(StringUtils.isEmpty(menuId)){
                continue;
            }
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(assignMenuVo.getRoleId());
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenuService.save(sysRoleMenu);
        }
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




