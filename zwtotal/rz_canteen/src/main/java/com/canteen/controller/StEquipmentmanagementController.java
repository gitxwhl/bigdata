package com.canteen.controller;

import com.canteen.service.StEquipmentmanagementService;
import com.canteen.utils.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/StEquipmentmanagement.do")
@Slf4j
@CrossOrigin
public class StEquipmentmanagementController {

    @Autowired
    private StEquipmentmanagementService stEquipmentmanagementService;

    //获取设备列表
    @RequestMapping(value = "/getEquipmentList", method = RequestMethod.POST)
    private PageBean getEquipmentList(HttpServletRequest request, @RequestBody String param){
        return stEquipmentmanagementService.getEquipmentList(param);
    }

    //删除设备
    @RequestMapping(value = "/deleteEquipment", method = RequestMethod.POST)
    private String deleteEquipment(HttpServletRequest request, @RequestBody String param){
        try {
            return stEquipmentmanagementService.deleteEquipment(param);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "出现异常,删除设备失败";
    }

    //新增设备
    @RequestMapping(value = "/insertEquipment", method = RequestMethod.POST)
    private String insertEquipment(HttpServletRequest request, @RequestBody String param){
        try {
            return stEquipmentmanagementService.insertEquipment(param);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "出现异常，新增设备失败";
    }


    //修改设备
    @RequestMapping(value = "/updateEquipment", method = RequestMethod.POST)
    private String updateEquipment(HttpServletRequest request, @RequestBody String param){
        try {
            return stEquipmentmanagementService.updateEquipment(param);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "出现异常，修改设备失败";
    }

    //查询关联属性(运维餐厅，设备状态)
    @RequestMapping(value = "/getRelevance", method = RequestMethod.GET)
    private Object getRelevance(HttpServletRequest request){
        return stEquipmentmanagementService.getRelevance();
    }
}
