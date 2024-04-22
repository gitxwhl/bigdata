package com.mashibing.driveuser.controller;
import com.mashibing.driveuser.service.DriverUserService;
import com.mashibing.internalcommon.constent.DriverCarConstans;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DriverUserExistsResposonse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private DriverUserService driverUserService;

    @RequestMapping("/findTest")
    public String findTest() {
        return "service-drive-user";
    }


    @RequestMapping("/findDriverService")
    public ResponseResult findDriverService(){
        return driverUserService.findDriverUser();
    }

    /**
     * 添加司机
     * @return
     */
    @RequestMapping("/users")
    public ResponseResult addDriverUser(@RequestBody DriverUser driverUser){
        return driverUserService.addDriverUser(driverUser);
    }

    /**
     * 根据id修改司机信息
     */
    @PutMapping("/users")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser){
        return driverUserService.updateUser(driverUser);
    }

    /**
     * 根据手机号查询用户是否存在
     */
    @GetMapping("/check-driver/{driverPhone}")
    public ResponseResult<DriverUserExistsResposonse> getDriverUserbyPhone(@PathVariable("driverPhone") String driverPhone){
        ResponseResult<DriverUser> driverUserbyPhone = driverUserService.getDriverUserbyPhone(driverPhone);
        DriverUser driverUserDb = driverUserbyPhone.getData();
        DriverUserExistsResposonse resposonse=new DriverUserExistsResposonse();
        int isExists=DriverCarConstans.EXISTS;
        if(driverUserDb==null){
            isExists= DriverCarConstans.NOT_EXISTS;
            resposonse.setDriverPhone(driverPhone);
            resposonse.setIsExists(isExists);
        }else {
            resposonse.setDriverPhone(driverPhone);
            resposonse.setIsExists(isExists);
        }
        return ResponseResult.success(resposonse);
    }



}
