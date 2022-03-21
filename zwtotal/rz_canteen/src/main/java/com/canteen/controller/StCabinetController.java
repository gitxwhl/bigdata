package com.canteen.controller;

import com.canteen.service.StCabinetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/StCabinet.do")
@Slf4j
@CrossOrigin
public class StCabinetController {

    @Autowired
    private StCabinetService stCabinetService;

    //餐柜管理列表
    @RequestMapping(value = "/getCabinet", method = RequestMethod.POST)
    private Object getCabinet(@RequestBody String param) {
        try {
            return stCabinetService.getCabinet(param);
        } catch (Exception e) {
            e.printStackTrace();
            return "获取餐柜管理列表失败";
        }
    }

    //查看订单详情
    @RequestMapping(value = "/getOrderInfo", method = RequestMethod.POST)
    private Object getOrderInfo(@RequestBody String param) {
        try {
            return stCabinetService.getOrderInfo(param);
        } catch (Exception e) {
            e.printStackTrace();
            return "查看订单详情失败";
        }
    }

    //餐柜上货
    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    private Object addOrder(@RequestBody String param) {
        try {
            return stCabinetService.addOrder(param);
        } catch (Exception e) {
            e.printStackTrace();
            return "操作失败";
        }
    }

    //更换订单
    @RequestMapping(value = "/updateOrder", method = RequestMethod.POST)
    private Object updateOrder(@RequestBody String param) {
        try {
            return stCabinetService.updateOrder(param);
        } catch (Exception e) {
            e.printStackTrace();
            return "操作失败";
        }
    }

    //清空餐柜
    @RequestMapping(value = "/emptyCabinet", method = RequestMethod.POST)
    private Object emptyCabinet() {
        try {
            return stCabinetService.emptyCabinet();
        } catch (Exception e) {
            e.printStackTrace();
            return "清空餐柜失败";
        }
    }

    //获取下拉框数据
    @RequestMapping(value = "/dropDownBox", method = RequestMethod.GET)
    private Object dropDownBox() {
        try {
            return stCabinetService.dropDownBox();
        } catch (Exception e) {
            e.printStackTrace();
            return "获取下拉框数据失败";
        }
    }
}
