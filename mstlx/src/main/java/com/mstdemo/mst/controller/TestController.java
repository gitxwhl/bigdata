package com.mstdemo.mst.controller;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.mstdemo.mst.bean.DeptInfo;
import com.mstdemo.mst.bean.Permission;
import com.mstdemo.mst.exception.CommonResult;
import com.mstdemo.mst.service.DeptInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.nio.channels.Channel;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Controller
@RequestMapping("/test")
@Validated
public class TestController {
    @Autowired
    private DeptInfoService deptInfoService;

    /**
     * 测试返回主键id
     *
     * @param deptInfo
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public CommonResult add(@RequestBody @Valid DeptInfo deptInfo) {
        int su = deptInfoService.addDept(deptInfo);
        CommonResult commonResult;
        if(su>0){
            commonResult = new CommonResult(200,"添加成功");
        }else {
            commonResult = new CommonResult(500,"添加失败");
        }
        return commonResult;
    }

    /**
     * 入参不是用对象绑定，验证非空
     */
    @RequestMapping("/getDeptInfoById")
    @ResponseBody
    public CommonResult getDeptInfoById( @NotBlank(message = "部门名称不能为空")  @RequestParam("deptName") String deptName) {
        List<DeptInfo> list= deptInfoService.getDeptInfoById(deptName);
        CommonResult commonResult = new CommonResult(200,"查询成功",list);
        return commonResult;
    }





    /**
     * 递归调用
     *
     * @return
     */
    @RequestMapping("/findPermission")
    public List<Permission> findPermission() {
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

    /**
     * 导出
     */
    @RequestMapping(value = "/StOnlineSupermarketExport", method = RequestMethod.POST)
    public void batchExportGood(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) {


    }


    /**
     * 将数据导成excle并上传到服务器
     */
    @RequestMapping("/exl")
    public void exl() {
        deptInfoService.exl();
    }


    /**
     * 分批导入
     */
    @RequestMapping("/fpexl")
    public void fpexl() {
        deptInfoService.fpexl();
    }


    /**
     * 测试  全局异常捕获
     */
    @RequestMapping("/textException")
    public void textException(){
        int i=10/0;
    }


}
