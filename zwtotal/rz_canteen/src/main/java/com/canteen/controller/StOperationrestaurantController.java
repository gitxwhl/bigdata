package com.canteen.controller;

import com.canteen.entity.StOperationrestaurant;
import com.canteen.service.StOperationrestaurantService;
import com.canteen.utils.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/StOperationrestaurant.do")
@Slf4j
@CrossOrigin
public class StOperationrestaurantController {
    @Autowired
    private StOperationrestaurantService stOperationrestaurantService;

    //获取运维餐厅级别菜单
    @RequestMapping(value = "/getStOperationList",method = RequestMethod.POST)
    public List getStOperationList(@RequestBody StOperationrestaurant stOperationrestaurant){
        return stOperationrestaurantService.getStOperationList(stOperationrestaurant.getDeFlag());
    }

    //策略列表查询
    @RequestMapping(value = "/getStrategyList", method = RequestMethod.POST)
    private PageBean getStrategyList(HttpServletRequest request, @RequestBody String param){
        return stOperationrestaurantService.getStrategyList(param);
    }

    //删除策略
    @RequestMapping(value = "/deleteStrategy", method = RequestMethod.POST)
    private String deleteStrategy(HttpServletRequest request, @RequestBody String param){
        try {
            return stOperationrestaurantService.deleteStrategy(param);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "删除策略失败";
    }

    //获取关联属性
    /*@RequestMapping("/getEquipment")
    public Map getEquipment(){
        return stOperationrestaurantService.getEquipment();
    }*/

    //修改策略
    @RequestMapping(value = "/updateStrategy", method = RequestMethod.POST)
    private String updateStrategy(HttpServletRequest request, @RequestBody String param){
        try {
            return stOperationrestaurantService.updateStrategy(param);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "修改策略失败";
    }

    //新增策略
    @RequestMapping(value = "/insertStrategy", method = RequestMethod.POST)
    private Object insertStrategy(HttpServletRequest request, @RequestBody String param){
        try {
            return stOperationrestaurantService.insertStrategy(param);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "新增策略失败";
    }

    //获取字典表定义
    @RequestMapping(value = "/dictionaries", method = RequestMethod.GET)
    private Object dictionaries(HttpServletRequest request){
        return stOperationrestaurantService.dictionaries();
    }
}
