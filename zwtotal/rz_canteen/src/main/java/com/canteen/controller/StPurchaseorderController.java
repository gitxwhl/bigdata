package com.canteen.controller;

import com.canteen.service.StPurchaseorderService;
import com.canteen.utils.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/StPurchaseorder.do")
@Slf4j
@CrossOrigin
public class StPurchaseorderController {


    @Autowired
    private StPurchaseorderService stPurchaseorderService;

    //采购订单列表
    @RequestMapping(value = "/getPurchaseIndentList", method = RequestMethod.POST)
    private PageBean getStrategyList(HttpServletRequest request, @RequestBody String param) {
        return stPurchaseorderService.getPurchaseIndentList(param);
    }
}
