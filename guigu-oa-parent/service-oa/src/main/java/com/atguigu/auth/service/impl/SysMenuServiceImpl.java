package com.atguigu.auth.service.impl;

import atguigu.model.system.SysMenu;
import atguigu.model.system.SysRoleMenu;
import atguigu.model.system.SysUser;
import atguigu.vo.system.AssginMenuVo;
import atguigu.vo.system.MetaVo;
import atguigu.vo.system.RouterVo;
import com.atguigu.auth.service.SysMenuService;
import com.atguigu.auth.mapper.SysMenuMapper;
import com.atguigu.auth.service.SysRoleMenuService;
import com.atguigu.auth.utils.MenuHelper;
import com.atguigu.common.config.exception.GuiguException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author dell
 * @description 针对表【sys_menu(菜单表)】的数据库操作Service实现
 * @createDate 2024-06-23 15:26:41
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
        implements SysMenuService {
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
        wrapper.eq(SysMenu::getParentId, id);
        int count = this.count(wrapper);
        if (count > 0) {
            throw new GuiguException(201, "菜单下面有子菜单，不能删除");
        }
        this.removeById(id);
    }

    @Override
    public List<SysMenu> findMenuByRoleId(Long roleId) {
        //查询所有菜单，菜单状态为1
        LambdaUpdateWrapper<SysMenu> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysMenu::getStatus, 1);
        List<SysMenu> sysMenuList = this.list(wrapper);
        //根据用户角色id查询，角色菜单关系表查询对应菜单id
        LambdaUpdateWrapper<SysRoleMenu> sysRoleMenuWrapper = new LambdaUpdateWrapper<>();
        sysRoleMenuWrapper.eq(SysRoleMenu::getRoleId, roleId);
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuService.list(sysRoleMenuWrapper);
        //获取角色菜单id集合
        List<Long> menuList = sysRoleMenuList.stream().map(c -> c.getMenuId()).collect(Collectors.toList());
        //当前角色分配菜单在所有菜单中存在，设置 isSelect=true  否则isSelect=false
        //根据获取菜单id，获取对应菜单对象
        //拿着菜单id，和所有菜单集合里面id进行比较，如果相同封装
        sysMenuList.stream().forEach(item -> {
            if (menuList.contains(item.getId())) {
                item.setSelect(true);
            } else {
                item.setSelect(false);
            }
        });
        //返回菜单列表
        List<SysMenu> sysMenus = MenuHelper.buildTree(sysMenuList);
        return sysMenus;
    }

    @Transactional
    @Override
    public void doAssign(AssginMenuVo assignMenuVo) {
        //根据角色id删除角色菜单关系表已经分配的菜单
        LambdaUpdateWrapper<SysRoleMenu> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysRoleMenu::getRoleId, assignMenuVo.getRoleId());
        sysRoleMenuService.remove(wrapper);
        //获取入参已选择的菜单id集合，重新分配
        List<Long> menuIdList = assignMenuVo.getMenuIdList();
        for (Long menuId : menuIdList) {
            if (StringUtils.isEmpty(menuId)) {
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


    //    根据用户id获取用户可以操作菜单列表
    @Override
    public List<RouterVo> findUserMenuListByUserId(Long userId) {
        //判断当前用户是否是管理员，userid=1是管理员
        //        如果是管理员，查询所有菜单
        List<SysMenu> sysMenus = null;

        if (userId == 1) {
            LambdaUpdateWrapper<SysMenu> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(SysMenu::getStatus, 1);
            wrapper.orderByAsc(SysMenu::getSortValue);
            sysMenus = baseMapper.selectList(wrapper);
        } else {
//        如果不是管理员，根据userid查询菜单列表
//        多表查询：用户角色关系表，角色菜单关系表，菜单表
            sysMenus = baseMapper.findMenuListByUserId(userId);
        }


//        把查询出来的数据列表构建成框架要求的路由数据结构
        List<SysMenu> sysMenusTreeList = MenuHelper.buildTree(sysMenus);
        //构建成框架要求的路由数据结构
        List<RouterVo> routerVos = this.buildRouters(sysMenusTreeList);
        return routerVos;
    }

    private List<RouterVo> buildRouters(List<SysMenu> menus) {
//        最终返回集合
        List<RouterVo> menusList = new ArrayList<>();
        for (SysMenu menu : menus) {
//        封装返回对象
            RouterVo routerVo = new RouterVo();
            routerVo.setHidden(false);
            routerVo.setAlwaysShow(false);
            routerVo.setPath(getRouterPath(menu));
            routerVo.setComponent(menu.getComponent());
            routerVo.setMeta(new MetaVo(menu.getName(), menu.getIcon()));
//        下层数据部分
            List<SysMenu> children = menu.getChildren();
            //1 表示菜单  把隐藏路由加载出来
            //如果当前是菜单，需将按钮对应的路由加载出来，如：“角色授权”按钮对应的路由在“系统管理”下面
            if (menu.getType() == 1) {
                //设置加载下面的隐藏路由
                List<SysMenu> hiddenMenuList = children.stream().filter(item -> !StringUtils.isEmpty(item.getComponent()))
                        .collect(Collectors.toList());
                for (SysMenu hiddenMenu : hiddenMenuList) {
                    RouterVo routerHidden = new RouterVo();
                    //表示是一个隐藏路由
                    routerHidden.setHidden(true);
                    routerHidden.setAlwaysShow(false);
                    routerHidden.setPath(getRouterPath(hiddenMenu));
                    routerHidden.setComponent(hiddenMenu.getComponent());
                    routerHidden.setMeta(new MetaVo(hiddenMenu.getName(), hiddenMenu.getIcon()));
                    menusList.add(routerHidden);

                }
            } else {
                if (!CollectionUtils.isEmpty(children)) {
                    if (children.size() > 0) {
                        routerVo.setAlwaysShow(true);
                    }
                    //递归
                    routerVo.setChildren(buildRouters(children));
                }
            }
            menusList.add(routerVo);
        }

        return menusList;

    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenu menu) {
        String routerPath = "/" + menu.getPath();
        if (menu.getParentId().intValue() != 0) {
            routerPath = menu.getPath();
        }
        return routerPath;
    }


    //    根据用户id获取用户可以操作按钮的列表
    @Override
    public List<String> findUserPermsByUserId(Long userId) {
        //判断当前用户是否是管理员，userid=1是管理员
        //        如果是管理员，查询所有按钮
        List<SysMenu> sysMenus = null;
        if(userId==1){
            LambdaUpdateWrapper<SysMenu> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(SysMenu::getStatus, 1);
            sysMenus = baseMapper.selectList(wrapper);
        }else{
            //        如果不是管理员，根据userid查询可以操作按钮列表
            //        多表查询：用户角色关系表，角色菜单关系表，菜单表
            sysMenus = baseMapper.findMenuListByUserId(userId);
        }
        //        从查询出来的数据里面，获取可以操作按钮的值的list集合，返回
        List<String> paramList = sysMenus.stream().filter(item -> item.getType() == 2)
                .map(item -> item.getPerms()).collect(Collectors.toList());
        return paramList;
    }





//    =================================================================================



    //4 根据用户id获取用户可以操作菜单列表
//    @Override
//    public List<RouterVo> findUserMenuListByUserId(Long userId) {
//        List<SysMenu> sysMenuList = null;
//        //1 判断当前用户是否是管理员   userId=1是管理员
//        //1.1 如果是管理员，查询所有菜单列表
//        if(userId.longValue() == 1) {
//            //查询所有菜单列表
//            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
//            wrapper.eq(SysMenu::getStatus,1);
//            wrapper.orderByAsc(SysMenu::getSortValue);
//            sysMenuList = baseMapper.selectList(wrapper);
//        } else {
//            //1.2 如果不是管理员，根据userId查询可以操作菜单列表
//            //多表关联查询：用户角色关系表 、 角色菜单关系表、 菜单表
//            sysMenuList = baseMapper.findMenuListByUserId(userId);
//        }
//
//        //2 把查询出来数据列表-构建成框架要求的路由结构
//        //使用菜单操作工具类构建树形结构
//        List<SysMenu> sysMenuTreeList = MenuHelper.buildTree(sysMenuList);
//        //构建成框架要求的路由结构
//        List<RouterVo> routerList = this.buildRouter(sysMenuTreeList);
//        return routerList;
//    }
//
//    //构建成框架要求的路由结构
//    private List<RouterVo> buildRouter(List<SysMenu> menus) {
//        //创建list集合，存储最终数据
//        List<RouterVo> routers = new ArrayList<>();
//        //menus遍历
//        for(SysMenu menu : menus) {
//            RouterVo router = new RouterVo();
//            router.setHidden(false);
//            router.setAlwaysShow(false);
//            router.setPath(getRouterPath(menu));
//            router.setComponent(menu.getComponent());
//            router.setMeta(new MetaVo(menu.getName(), menu.getIcon()));
//            //下一层数据部分
//            List<SysMenu> children = menu.getChildren();
//            if(menu.getType().intValue() == 1) {
//                //加载出来下面隐藏路由
//                List<SysMenu> hiddenMenuList = children.stream()
//                        .filter(item -> !StringUtils.isEmpty(item.getComponent()))
//                        .collect(Collectors.toList());
//                for(SysMenu hiddenMenu : hiddenMenuList) {
//                    RouterVo hiddenRouter = new RouterVo();
//                    //true 隐藏路由
//                    hiddenRouter.setHidden(true);
//                    hiddenRouter.setAlwaysShow(false);
//                    hiddenRouter.setPath(getRouterPath(hiddenMenu));
//                    hiddenRouter.setComponent(hiddenMenu.getComponent());
//                    hiddenRouter.setMeta(new MetaVo(hiddenMenu.getName(), hiddenMenu.getIcon()));
//
//                    routers.add(hiddenRouter);
//                }
//
//            } else {
//                if(!CollectionUtils.isEmpty(children)) {
//                    if(children.size() > 0) {
//                        router.setAlwaysShow(true);
//                    }
//                    //递归
//                    router.setChildren(buildRouter(children));
//                }
//            }
//            routers.add(router);
//        }
//        return routers;
//    }
//
//    /**
//     * 获取路由地址
//     *
//     * @param menu 菜单信息
//     * @return 路由地址
//     */
//    public String getRouterPath(SysMenu menu) {
//        String routerPath = "/" + menu.getPath();
//        if(menu.getParentId().intValue() != 0) {
//            routerPath = menu.getPath();
//        }
//        return routerPath;
//    }
//
//    //5 根据用户id获取用户可以操作按钮列表
//    @Override
//    public List<String> findUserPermsByUserId(Long userId) {
//        //1 判断是否是管理员，如果是管理员，查询所有按钮列表
//        List<SysMenu> sysMenuList = null;
//        if(userId.longValue() == 1) {
//            //查询所有菜单列表
//            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
//            wrapper.eq(SysMenu::getStatus,1);
//            sysMenuList = baseMapper.selectList(wrapper);
//        } else {
//            //2 如果不是管理员，根据userId查询可以操作按钮列表
//            //多表关联查询：用户角色关系表 、 角色菜单关系表、 菜单表
//            sysMenuList = baseMapper.findMenuListByUserId(userId);
//        }
//
//        //3 从查询出来的数据里面，获取可以操作按钮值的list集合，返回
//        List<String> permsList = sysMenuList.stream()
//                .filter(item -> item.getType() == 2)
//                .map(item -> item.getPerms())
//                .collect(Collectors.toList());
//
//        return permsList;
//    }

}




