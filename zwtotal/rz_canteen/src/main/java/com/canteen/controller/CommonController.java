package com.canteen.controller;

import com.canteen.service.ICommonService;
import com.canteen.utils.ResultWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lilong
 * @Date: 2020/11/24 09:41
 * @Description: 用于提供常用的公共性查询
 */
@RestController
@RequestMapping("/common.do")
@CrossOrigin
public class CommonController {
    @Autowired
    private ICommonService commonService;

    @ApiOperation(value = "查询餐厅列表或超市列表 0:餐厅，1：超市")
    @GetMapping("/getStOperationList/{type}")
    public List getStOperationList(@PathVariable("type") String type){
        return commonService.getStOperationList(type);
    }

    @ApiOperation(value = "获取公司部门信息")
    @RequestMapping(value = "/getCompanyOrDeptList/{type}/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultWrapper getCompanyOrDeptList(@PathVariable("type") String type,@PathVariable("id") String id) {
        List<Map<String,String>> list = commonService.getCompanyOrDeptList(type,id);
        return  new ResultWrapper().success().data(list);
    }


//·······································································

    @ApiOperation(value = "获取商品类别")
    @RequestMapping(value = "/getGoodType", method = RequestMethod.POST)
    public @ResponseBody
    ResultWrapper getGoodType() {
        return new ResultWrapper().success().data(commonService.getGoodType());
    }

    @ApiOperation(value = "获取申请公司信息")
    @RequestMapping(value = "/findCompanyId", method = RequestMethod.POST)
    public
    ResultWrapper findCompanyId(@RequestBody Map<String,Object> map) {
        return new ResultWrapper().success().data(commonService.findCompanyId(map));
    }

    @ApiOperation(value = "获取申请部门信息")
    @RequestMapping(value = "/findDeparmentId", method = RequestMethod.POST)
    public @ResponseBody
    ResultWrapper findDeparmentId(@RequestBody Map<String,Object> map) {
        return new ResultWrapper().success().data(commonService.findDeparmentId(map));
    }


    @ApiOperation(value = "获取公司信息")
    @RequestMapping(value = "/findCompany", method = RequestMethod.POST)
    public @ResponseBody
    ResultWrapper findCompany() {
        return new ResultWrapper().success().data(commonService.findCompany());
    }
    @ApiOperation(value = "获取公司餐厅信息")
    @RequestMapping(value = "/getRestaurantList/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultWrapper getRestaurantList(@PathVariable("id") String id) {
        List<Map<String,String>> list = commonService.getRestaurantList(id);
        return  new ResultWrapper().success().data(list);
    }

    @ApiOperation(value = "获取部门信息")
    @RequestMapping(value = "/finddeparmentIds/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ResultWrapper finddeparmentIds(@PathVariable("id") String id) {
        List<Map<String,String>> deptmant = commonService.finddeparmentIds(id);
        return  new ResultWrapper().success().data(deptmant);
    }

    @ApiOperation(value = "获取部门内人员信息")
    @RequestMapping(value = "/getPersonList/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ResultWrapper getPersonList(@PathVariable("id") String id) {
        List<Map<String,String>> PersonList = commonService.getPersonList(id);
        return  new ResultWrapper().success().data(PersonList);
    }

}
