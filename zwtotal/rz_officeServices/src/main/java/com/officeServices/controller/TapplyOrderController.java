package com.officeServices.controller;

import com.officeServices.entity.ApplyOrder;
import com.officeServices.entity.Tstock;
import com.officeServices.service.TapplyOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/TapplyOrder.do")
@Slf4j
@CrossOrigin
public class TapplyOrderController {

    @Autowired
    private TapplyOrderService tapplyOrderService;

    /**
     * 提交办公服务用品申请
     */
    @RequestMapping(value = "/TapplyOrder", method = RequestMethod.POST)
    public Object submitApplyOrder(@RequestBody Map<String,Object> map){
        return tapplyOrderService.submitApplyOrder(map);
    }

    /**
     * 审批人查询审批列表
     */
    @RequestMapping(value = "/getApprovalList", method = RequestMethod.POST)
    public Object getApprovalList(@RequestBody Map<String,Object> map){
        try {
            return tapplyOrderService.getApprovalList(map);
        }catch (Exception e){
            e.printStackTrace();
            return "获取列表信息异常";
        }
    }

    /**
     * 审批人审批
     */
    @RequestMapping(value = "/approveApply", method = RequestMethod.POST)
    public Object approveApply(@RequestBody Map<String,Object> map){
        try {
            return tapplyOrderService.approveApply(map);
        }catch (Exception e){
            e.printStackTrace();
            return "审批异常";
        }
    }

    /**
     * 审批通过列表查询
     */
   /* @RequestMapping(value = "/getPassList", method = RequestMethod.POST)
    public Object getPassList(@RequestBody Map<String,Object> map){
        try {
            return tapplyOrderService.getPassList(map);
        }catch (Exception e){
            e.printStackTrace();
            return "获取列表信息异常";
        }
    }*/

    /**
     * 发放
     */
    @RequestMapping(value = "/grantGoods", method = RequestMethod.POST)
    public Object grantGoods(@RequestBody Map<String, Object> map){
        try {
            return tapplyOrderService.grantGoods(map);
        }catch (Exception e){
            e.printStackTrace();
            return "发放物品异常";
        }
    }

    /**
     * 下拉框
     */
    @RequestMapping(value = "/dropDownBox", method = RequestMethod.POST)
    public Object dropDownBox(@RequestBody Map<String, Object> map){
        try {
            return tapplyOrderService.dropDownBox(map);
        }catch (Exception e){
            e.printStackTrace();
            return "获取下拉框失败";
        }
    }

    /**
     * 通过id查询物品详情
     */
    @RequestMapping(value = "/getGoodInfo", method = RequestMethod.POST)
    public Object getGoodInfo(@RequestBody Map<String,Object> map){
        try {
            return tapplyOrderService.getGoodInfo(map);
        }catch (Exception e){
            e.printStackTrace();
            return "查询物品详情失败";
        }
    }


}
