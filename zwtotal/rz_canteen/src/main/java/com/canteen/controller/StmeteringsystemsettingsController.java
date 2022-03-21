package com.canteen.controller;
import com.canteen.entity.Stmeteringsystemsettings;
import com.canteen.service.StmeteringsystemsettingsService;
import com.canteen.utils.HttpUtils;
import com.canteen.utils.PageBean;
import com.canteen.utils.Result;
import com.canteen.utils.ResultInfo;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
@RequestMapping("/Stmeteringsystemsettings.do")
public class StmeteringsystemsettingsController {

    @Autowired
    private StmeteringsystemsettingsService stmeteringsystemsettingsService;
    /**
     * 根据名称和编码查询获取菜品类别列表
     * @param
     * @return
     */
    @RequestMapping(value = "/getStmeteringsystemsettingsPage", method = RequestMethod.POST)
    public void getStCategorydishesPage(HttpServletRequest req, HttpServletResponse resp, Stmeteringsystemsettings stmeteringsystemsettings) throws Exception {
        HttpUtils httpUtils = new HttpUtils();
        resp.setCharacterEncoding("UTF-8");
        stmeteringsystemsettings = (Stmeteringsystemsettings) httpUtils.getBean(req, resp, new TypeToken<Stmeteringsystemsettings>() {
        }.getType());
        PageBean<Stmeteringsystemsettings> pageBean = (PageBean<Stmeteringsystemsettings>) httpUtils.getBean(req, resp, new TypeToken<PageBean<Stmeteringsystemsettings>>() {
        }.getType());
        Result result = new Result();
        try {
            PageBean<Stmeteringsystemsettings> page = stmeteringsystemsettingsService.getStmeteringsystemsettingsPage(pageBean, stmeteringsystemsettings.getDishTime(),stmeteringsystemsettings.getWarningWeight(),stmeteringsystemsettings.getPlatedetectionTime());
            result.setCode("200");
            result.setMsg(page);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
            result.setMsg("服务器出错");
        }
        String resultStr = httpUtils.toJson(result);
        resp.getWriter().write(resultStr);
    }


    /**
     * 停启用
     */
    @RequestMapping("enableDiscontinue")
    public boolean enableDiscontinue(@RequestBody Stmeteringsystemsettings stmeteringsystemsettings){
        if(stmeteringsystemsettings.getId()!=null){
            return stmeteringsystemsettingsService.updateEnable(stmeteringsystemsettings);
        }
        return false;
    }


    /**
     *输入条件可查询为空时，设置添加
     */
    @RequestMapping("saveStmeteringsystemsettings")
    @ResponseBody
    public ResultInfo saveStCategorydishes(@RequestBody Stmeteringsystemsettings stmeteringsystemsettings, HttpServletRequest request, HttpServletResponse response){
        boolean flag = stmeteringsystemsettingsService.saveStmeteringsystemsettings(stmeteringsystemsettings);
        if (flag) {
            return ResultInfo.getSuccessInfo();
        } else {
            return ResultInfo.getFailedInfo();
        }
    }
//--------------------------------------------------------------------------------------------
    /**
     * 计量系统设置列表
     */
    @RequestMapping(value = "getStmetering",method = RequestMethod.POST)
    public Object getStmeteringsystemsettings(@RequestBody String param){
        try {
            return stmeteringsystemsettingsService.getStmetering(param);
        }catch (Exception e){
            e.printStackTrace();
            return "获取计量系统设置列表失败";
        }
    }

    /**
     * 状态变更
     */
    @RequestMapping(value = "updateState",method = RequestMethod.POST)
    public Object updateState(@RequestBody Stmeteringsystemsettings param){
        try {
            return stmeteringsystemsettingsService.updateState(param);
        }catch (Exception e){
            e.printStackTrace();
            return "状态变更失败";
        }
    }

    /**
     * 删除设置
     */
    @RequestMapping(value = "deleteStmeteringsystemsettings",method = RequestMethod.POST)
    public Object deleteStmeteringsystemsettings(@RequestBody Stmeteringsystemsettings param){
        try {
            return stmeteringsystemsettingsService.deleteStmeteringsystemsettings(param);
        }catch (Exception e){
            e.printStackTrace();
            return "删除设置失败";
        }
    }

    /**
     * 新增设置
     */
    @RequestMapping(value = "addStmeteringsystemsettings",method = RequestMethod.POST)
    public Object addStmeteringsystemsettings(@RequestBody Stmeteringsystemsettings param){
        try {
            return stmeteringsystemsettingsService.addStmeteringsystemsettings(param);
        }catch (Exception e){
            e.printStackTrace();
            return "新增设置失败";
        }
    }

    /**
     * 获取下拉框数据
     */
    @RequestMapping(value = "dropDownBox",method = RequestMethod.GET)
    public Object dropDownBox(){
        try {
            return stmeteringsystemsettingsService.dropDownBox();
        }catch (Exception e){
            e.printStackTrace();
            return "获取下拉框数据失败";
        }
    }
}
