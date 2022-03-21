package com.canteen.controller;

import com.canteen.entity.StShopOrder;
import com.canteen.service.ICommonService;
import com.canteen.service.IShopOrderService;
import com.canteen.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lilong
 * @Date: 2020/11/26 16:47
 * @Description: 商店，订单，商品
 */

@RestController
@RequestMapping("/shopOrder.do")
public class StShopOrdersController {

    @Autowired
    private IShopOrderService service;



    @ApiOperation(value = "超市订单综合查询接口")
    @RequestMapping(value = "/getshopOrderList",method = RequestMethod.POST)
    public Map<String,Object> getshopOrderList(@RequestBody StShopOrder entity){

        return service.getshopOrderList(entity);
    }

    @ApiOperation(value = "订单详情查询")
    @GetMapping("/getOrderDetail/{orderId}")
    public Map<String,Object> getOrderDetail(@PathVariable("orderId") String orderId){

        return service.getOrderDetail(orderId);
    }
}