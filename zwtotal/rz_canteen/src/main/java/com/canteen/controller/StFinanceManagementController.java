package com.canteen.controller;

import com.canteen.service.StFinanceManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/StFinanceManagement.do")
@Slf4j
@CrossOrigin
public class StFinanceManagementController {

    @Autowired
    private StFinanceManagementService stFinanceManagementService;

    /*
    * 就餐结算明细列表
    * */
    @RequestMapping(value = "/detailList",method = RequestMethod.POST)
    public Object detailList(@RequestBody String param){
        try {
            return stFinanceManagementService.detailList(param);
        }catch (Exception e){
            e.printStackTrace();
            return "获取就餐结算明细列表失败";
        }
    }

    /*
     * 食堂经营成本统计
     * */
    @RequestMapping(value = "/costStatistics",method = RequestMethod.POST)
    public Object costStatistics(@RequestBody String param){
        try {
            return stFinanceManagementService.costStatistics(param);
        }catch (Exception e){
            e.printStackTrace();
            return "获取食堂经营成本统计失败";
        }
    }
}
