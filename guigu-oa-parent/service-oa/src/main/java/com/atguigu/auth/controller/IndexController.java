package com.atguigu.auth.controller;

import atguigu.model.system.SysMenu;
import atguigu.model.system.SysUser;
import atguigu.vo.system.LoginVo;
import atguigu.vo.system.RouterVo;
import com.atguigu.auth.service.SysMenuService;
import com.atguigu.auth.service.SysUserService;
import com.atguigu.common.config.exception.GuiguException;
import com.atguigu.jwt.JwtHelper;
import com.atguigu.common.result.Result;
import com.atguigu.common.utils.MD5;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "后台登录管理")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    //    @PostMapping("login")
//    public Result login(){
////        {"code":20000,"data":{"token":"admin-token"}}
//        Map<Object, Object> map = new HashMap<>();
//       map.put("token","admin-token");
//       return Result.ok(map);
//    }
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation("登录")
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo) {
        //获取输入的用户名和密码

        //根据用户名查询数据库
        LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysUser::getUsername, loginVo.getUsername());
        SysUser user = sysUserService.getOne(wrapper);
        //判断用户是否存在
        if (user == null) {
            throw new GuiguException(201, "用户不存在");
        }
        //判断密码是否正确
        String password_rc = loginVo.getPassword();
        String encrypt_rc = MD5.encrypt(password_rc);
        String password_db = user.getPassword();
        if (!encrypt_rc.equals(password_db)) {
            throw new GuiguException(201, "密码错误");
        }
        //判断状态是否被锁定
        Integer status = user.getStatus();
        if (status == 0) {
            throw new GuiguException(201, "账号被锁定");
        }
        //使用jwt根据用id和用户名称生成token
        String token = JwtHelper.createToken(user.getId(), user.getUsername());
        //返回数据
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return Result.ok(map);
    }

//    {"code":20000,"data":{"roles":["admin"],"introduction":"I am a super administrator","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif","name":"Super Admin"}}

//    @GetMapping("info")
//    public Result info() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("roles","[admin]");
//        map.put("name","admin");
//        map.put("avatar","https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
//        return Result.ok(map);
//    }

    @ApiOperation("获取用户信息")
    @GetMapping("info")
    public Result info(HttpServletRequest request) {
        //从请求头获取用户信息（获取请求头token
        String header = request.getHeader("header");
        // 从token中获取用户id或者名称
        Long userId = 2L;
//        Long userId = JwtHelper.getUserId(header);
        //根据用户id或者名称查询数据库，获取用户信息
        SysUser sysUser = sysUserService.getById(userId);
        //根据用户id获取用户可以操作菜单列表
        //查询数据库动态构建路由结构，进行显示
        List<RouterVo> RouterList = sysMenuService.findUserMenuListByUserId(userId);


        //根据用户id获取用户可以操作按钮列表
        List<String> paramList = sysMenuService.findUserPermsByUserId(userId);


        HashMap<String, Object> map = new HashMap<>();


        //  返回用户可以操作的菜单
        map.put("routers", RouterList);
        // 返回用户可以操作的按钮
        map.put("buttons", paramList);
        return Result.ok(map);
    }


    /**
     * 退出
     *
     * @return
     */
    @PostMapping("logout")
    public Result logout() {
        return Result.ok();
    }


}
