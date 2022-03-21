package com.canteen.controller;

import com.canteen.service.StAccurateChoosingMealsService;
import com.canteen.service.StquantitypriceService;
import com.canteen.utils.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/StQuantityprice.do")
@Slf4j
@CrossOrigin
public class StQuantitypriceController {

    @Autowired
    private StquantitypriceService stquantitypriceService;

    //获取商品价格管理列表
    @RequestMapping(value = "/getCommodityList", method = RequestMethod.POST)
    private PageBean getNutritionList(HttpServletRequest request, @RequestBody String param){
        return stquantitypriceService.getCommodityList(param);
    }


    //列表单击修改
    @RequestMapping(value = "/amendPrice", method = RequestMethod.POST)
    private String amendPrice(HttpServletRequest request, @RequestBody String param){
        return stquantitypriceService.amendPrice(param);
    }

}
