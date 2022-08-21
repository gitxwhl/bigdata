package com.mstdemo.mst.controller;
import com.mstdemo.mst.bean.DeptInfo;
import com.mstdemo.mst.bean.Permission;
import com.mstdemo.mst.service.DeptInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    private DeptInfoService deptInfoService;
    /**
     * 测试返回主键id
     * @param deptInfo
     * @return
     */
    @RequestMapping("/add")
    public int add(@RequestBody DeptInfo deptInfo) {
        return deptInfoService.addDept(deptInfo);
    }
    /**
     * 递归调用
     * @return
     */
    @RequestMapping("/findPermission")
    public List<Permission> findPermission(){
        List<Permission> permissions = deptInfoService.findPermission();
        return permissions;
    }

    /**
     * 页面跳转转发
     */
//    @RequestMapping(value= "/test01/{name}",method = RequestMethod.GET)
//    @ResponseBody
//    public String test(@PathVariable() String name){
//            return "forword: www.baidu.com";
//    }
//
//    @RequestMapping(value= "/test02/{name}",method = RequestMethod.GET)
//    public void test(@PathVariable() String name, HttpServletRequest request, HttpServletResponse response) throws Exception{
//        request.getRequestDispatcher("www.baidu.com").forward(request,response);
//    }




    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "test";
    }





}
