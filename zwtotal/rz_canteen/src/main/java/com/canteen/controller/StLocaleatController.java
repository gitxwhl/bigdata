package com.canteen.controller;

import com.canteen.entity.StLocaleat;
import com.canteen.service.StLocaleatService;
import com.canteen.utils.SigGenerate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@RestController
@RequestMapping("/StLocaleat.do")
@Slf4j
@CrossOrigin
public class StLocaleatController {
    @Autowired
    StLocaleatService stLocaleatService;

    //添加申请
    @RequestMapping(value = "/addApplication",method = RequestMethod.POST)
    public Object addApplication(HttpServletRequest request, @RequestBody StLocaleat stLocaleat){
        try {
            return stLocaleatService.addApplication(stLocaleat);
        }catch (Exception e){
            e.printStackTrace();
            return "出现异常,添加失败";
        }
    }

    //申请人查询保存的申请
    @RequestMapping(value = "/getSave",method = RequestMethod.POST)
    public Object getSave(HttpServletRequest request, @RequestBody String param){
        try {
            return stLocaleatService.getSave(param);
        }catch (Exception e){
            e.printStackTrace();
            return "查询保存失败";
        }
    }

    //申请人修改保存的申请
    @RequestMapping(value = "/updateSave",method = RequestMethod.POST)
    public Object updateSave(HttpServletRequest request, @RequestBody StLocaleat stLocaleat){
        try {
            return stLocaleatService.updateSave(stLocaleat);
        }catch (Exception e){
            e.printStackTrace();
            return "出现异常，修改失败";
        }
    }

    //申请人删除保存的申请
    @RequestMapping(value = "/deleteSave",method = RequestMethod.POST)
    public Object deleteSave(HttpServletRequest request, @RequestBody StLocaleat stLocaleat){
        try {
            return stLocaleatService.deleteSave(stLocaleat);
        }catch (Exception e){
            e.printStackTrace();
            return "出现异常，删除失败";
        }
    }

    //审批人查询已提交的申请
    @RequestMapping(value = "/getSubmit",method = RequestMethod.POST)
    public Object getSubmit(HttpServletRequest request, @RequestBody String param){
        try {
            return stLocaleatService.getSubmit(param);
        }catch (Exception e){
            e.printStackTrace();
            return "查询提交失败";
        }
    }

    //审批人进行审批
    @RequestMapping(value = "/approval",method = RequestMethod.POST)
    public Object approval(HttpServletRequest request, @RequestBody StLocaleat stLocaleat){
        try {
            return stLocaleatService.approval(stLocaleat);
        }catch (Exception e){
            e.printStackTrace();
            return "出现异常，审批失败";
        }
    }

    //查询关联属性
    @RequestMapping(value = "/getRelevance",method = RequestMethod.GET)
    public Object getRelevance(HttpServletRequest request){
            return stLocaleatService.getRelevance();
    }

    //申请人查询待办
    @RequestMapping(value = "/getBacklog",method = RequestMethod.POST)
    public Object getBacklog(HttpServletRequest request, @RequestBody String param){
        try {
            return stLocaleatService.getBacklog(param);
        }catch (Exception e){
            e.printStackTrace();
            return "查询待办失败";
        }
    }

}
