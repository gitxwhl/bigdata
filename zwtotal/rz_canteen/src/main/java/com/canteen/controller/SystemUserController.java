package com.canteen.controller;
import com.canteen.constant.BaseCode;
import com.canteen.entity.Stpersonnel;
import com.canteen.entity.SystemUser;
import com.canteen.service.SystemUserService;
import com.canteen.utils.Result;
import com.canteen.utils.ResultInfo;
import com.canteen.utils.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/SystemUser.do")
@Slf4j
@CrossOrigin
public class SystemUserController {

    @Autowired
    private SystemUserService systemUserService;


//    @RequestMapping("/getLogin")
//    private ResultInfo getLogin(Model model, SystemUser systemUser, HttpSession session){
//        SystemUser user= systemUserService.findSystemUser(systemUser);
//        Result result = new Result();
//        if(user != null){
//            session.setAttribute("loginuser",systemUser);
//            return ResultInfo.getSuccessInfo();
//        }else {
//            return ResultInfo.getFailedInfo();
//        }
//    }

    /**
     * 注銷
     * @param
     * @return
     */
//    @RequestMapping("/loginout")
////    private String loginout(HttpSession session){
////        session.invalidate();
////        return "redirect:index";
////    }


    /**
     * 登录用户人员基本信息体检指标
     * name
     * @return
     */
    @RequestMapping(value = "/SystemUser",method = RequestMethod.GET)
    private Results<List<SystemUser>> getSystemUser(@RequestBody SystemUser systemUser){
        List<SystemUser> SystemUserlist= systemUserService.findSystemUser(systemUser);
        return  new Results<List<SystemUser>>(BaseCode.BaseResultCode.SUCSESS,"",SystemUserlist);
    }










}
