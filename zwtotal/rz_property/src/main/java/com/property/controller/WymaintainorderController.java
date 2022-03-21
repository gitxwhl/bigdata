package com.property.controller;

import com.property.service.WymaintainorderService;
import com.property.utils.PageBean;
import com.property.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/StWymaintainorder.do")
@Slf4j
@CrossOrigin
public class WymaintainorderController {

    @Autowired
    WymaintainorderService wymaintainorderService;

    /**
     * 获取工单信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/getMaintainorder",method = RequestMethod.POST)
    public PageBean selectEquipmentInspection(HttpServletRequest request, @RequestBody String param){
        return wymaintainorderService.getMaintainorder(param);
    }
    /**
     * 获取工单状态
     * @param request
     * @return
     */
    @RequestMapping(value = "/getWorkState",method = RequestMethod.POST)
    public Object getPolicyAndWorkState(HttpServletRequest request){
        try{
            return wymaintainorderService.getWorkState();
        }catch (Exception e){
            e.printStackTrace();
            return "获取信息失败";
        }
    }

    /**
     * 获取工单详情信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/getMaintainorderDetails",method = RequestMethod.POST)
    public Object getMaintainorderDetails(HttpServletRequest request, @RequestBody String param){
        try{
            return wymaintainorderService.getMaintainorderDetails(param);
        }catch (Exception e){
            e.printStackTrace();
            return "获取信息失败";
        }
    }

    /**
     * 修改工单详情
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateSingle",method = RequestMethod.POST)
    public Result updateSingle(HttpServletRequest request,@RequestBody String param){
        Result result = new Result();
        try {
            wymaintainorderService.updateSingle(param);
            result.setMessage("修改成功");
        }catch (Exception e){
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 获取超期订单信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/getBeyondOrder",method = RequestMethod.POST)
    public PageBean getBeyondOrder(HttpServletRequest request, @RequestBody String param){
        return wymaintainorderService.getBeyondOrder(param);
    }

    /**
     * 重新生成超期订单告警
     * @param request
     */
    @RequestMapping(value = "/reOrderAlarm",method = RequestMethod.POST)
    public Result reOrderAlarm(HttpServletRequest request, @RequestBody String param){
        Result result = new Result();
        try {
            wymaintainorderService.reOrderAlarm(param);
            result.setMessage("重新生成超期订单成功");
        }catch (Exception e){
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 取消超期订单告警
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateOrderAlarm",method = RequestMethod.POST)
    public Result updateOrderAlarm(HttpServletRequest request, @RequestBody String param){
        Result result = new Result();
        try {
            wymaintainorderService.updateOrderAlarm(param);
            result.setMessage("告警取消成功");
        }catch (Exception e){
            result.setMessage(e.getMessage());
        }
        return result;
    }
}
