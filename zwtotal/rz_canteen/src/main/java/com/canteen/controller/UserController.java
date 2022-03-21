package com.canteen.controller;


import com.canteen.entity.User;
import com.canteen.service.UserService;
import com.canteen.utils.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/User.do")
@Slf4j
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 获取用户信息
     */
    @ApiOperation(value = "获取登录用户信息")
    @RequestMapping(value = "/getUserPerson", method = RequestMethod.POST)
    public Result getUserPerson(@RequestBody User user) {
        Result result = new Result();
        try{
            List<Map<String,String>> approvelPersoninfo = userService.getUserPerson(user);
            result.setCode("200");
            result.setMsg(approvelPersoninfo);
        }catch (Exception e){
            e.printStackTrace();
            result.setCode("500");
            result.setMsg("服务器出错");
        }
        return  result;
    }








}
