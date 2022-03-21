package com.canteen.controller;
import com.canteen.entity.*;
import com.canteen.entity.vo.AppTApplyCanteenVO;
import com.canteen.entity.vo.RemoteDiningListVO;
import com.canteen.service.IAppTApplyCanteenService;
import com.canteen.service.IAppTFlowService;
import com.canteen.service.IAppTFlowdetailService;
import com.canteen.utils.HttpUtils;
import com.canteen.utils.PageBean;
import com.canteen.utils.Result;
import com.canteen.utils.ResultWrapper;
import com.google.gson.reflect.TypeToken;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  异地就餐
 * </p>
 *
 * @author cdh
 * @since 2020-10-13
 */
@RestController
@RequestMapping("/appTApplyCanteen.do")
@CrossOrigin
public class AppTApplyCanteenController {

    @Autowired
    private IAppTApplyCanteenService appTApplyCanteenService;
    @Autowired
    private IAppTFlowService iAppTFlowService;
    @Autowired
    private IAppTFlowdetailService appTFlowdetailService;

    /**
     * 获取审批人
     * @param
     */
    @ApiOperation(value = "获取审批人")
    @RequestMapping(value = "/getApprovelPerson", method = RequestMethod.POST)
    public @ResponseBody
    ResultWrapper getApprovelPerson(@RequestBody User user) {
        List<Map<String,String>> approvelPersoninfo = appTApplyCanteenService.getApprovelPerson(user.getAccount());
        return  new ResultWrapper().success().data(approvelPersoninfo);
    }

    /**
     * 获取公司列表
     * @param
     */
    @ApiOperation(value = "获取公司列表")
    @RequestMapping(value = "/getCompanyList", method = RequestMethod.POST)
    public ResultWrapper getCompanyList(@RequestBody Map map) {

        List<Map<String,String>> companyList = appTApplyCanteenService.getCompanyList();

        return  new ResultWrapper().success().data(companyList);
    }


    /**
     * 获取食堂列表
     * @param
     */
    @ApiOperation(value = "获取食堂列表")
    @RequestMapping(value = "/getRestaurantList", method = RequestMethod.POST)
    public @ResponseBody
    ResultWrapper getRestaurantList(@RequestBody Map map) {
        List<Map<String,String>> restaurantList = appTApplyCanteenService.getRestaurantList(map);
        return  new ResultWrapper().success().data(restaurantList);
    }

    /**
     * 获取下级部门的信息
     * @param
     */
    @ApiOperation(value = "获取下级部门的信息")
    @RequestMapping(value = "/getNextDeptInfo", method = RequestMethod.POST)
    public @ResponseBody
    ResultWrapper getNextDeptInfo(@RequestBody Map map) {
        List<Map<String,String>> nextDeptInfo = appTApplyCanteenService.getNextDeptInfo(map);
        return  new ResultWrapper().success().data(nextDeptInfo);
    }

    /**
     * 获取部门内人员信息
     * @param
     */
    @ApiOperation(value = "获取部门内人员信息")
    @RequestMapping(value = "/getPersonList", method = RequestMethod.POST)
    public @ResponseBody
    ResultWrapper getPersonList(@RequestBody Map map) {
        List<Map<String,String>> PersonList = appTApplyCanteenService.getPersonList(map);
        return  new ResultWrapper().success().data(PersonList);
    }


    /**
     * 获取申请餐厅列表
     * @param
     */
    @ApiOperation(value = "获取申请餐厅列表")
    @RequestMapping(value = "/getApplyResList", method = RequestMethod.POST)
    public @ResponseBody
    ResultWrapper getApplyResList(@RequestBody Map map) {
        List<Map<String,String>> PersonList = appTApplyCanteenService.getApplyResList(map);
        return  new ResultWrapper().success().data(PersonList);
    }

    /**
     * 异地就餐申请
     * @param
     */
    @ApiOperation(value = "异地就餐申请")
    @RequestMapping(value = "/applyRemoteDining", method = RequestMethod.POST)
    public @ResponseBody ResultWrapper applyRemoteDining(@RequestBody AppTApplyCanteenVO appTApplyCanteenVO) {
        appTApplyCanteenService.applyRemoteDining(appTApplyCanteenVO);
        return  new ResultWrapper().success().data("申请成功");
    }

    /**
     * 异地就餐列表
     * @param
     */
    /*@ApiOperation(value = "异地就餐列表")
    @RequestMapping(value = "/getRemoteDiningList", method = RequestMethod.POST)
    public @ResponseBody
    ResultWrapper getRemoteDiningList(@RequestBody RemoteDiningListVO remoteDiningListVO) {
        IPage<Map<String, Object>> resultList = appTApplyCanteenService.getRemoteDiningList(remoteDiningListVO);
        return  new ResultWrapper().success().data(resultList);
    }
    @ApiOperation(value = "异地就餐审批列表")
    @RequestMapping(value = "/getRemoteDiningApprovelList", method = RequestMethod.POST)
    public @ResponseBody
    ResultWrapper getRemoteDiningApprovelList(@RequestBody RemoteDiningListVO remoteDiningListVO) {
        IPage<Map<String, Object>> resultList = appTApplyCanteenService.getRemoteDiningApprovelList(remoteDiningListVO);
        return  new ResultWrapper().success().data(resultList);
    }*/
    /**
     * 异地就餐审批列表
     * @param
     */
    @RequestMapping(value = "/AppTFlowdetail", method = RequestMethod.POST)
    public void getAppTFlowdetailPage(HttpServletRequest req, HttpServletResponse resp, RemoteDiningListVO remoteDiningListVO) throws Exception {
        HttpUtils httpUtils = new HttpUtils();
        resp.setCharacterEncoding("UTF-8");
        remoteDiningListVO = (RemoteDiningListVO) httpUtils.getBean(req, resp, new TypeToken<RemoteDiningListVO>() {
        }.getType());
        PageBean<AppTFlowdetail> pageBean = (PageBean<AppTFlowdetail>) httpUtils.getBean(req, resp, new TypeToken<PageBean<AppTFlowdetail>>() {
        }.getType());
        Result result = new Result();
        try {
            PageBean<AppTFlowdetail> page = appTApplyCanteenService.getAppTFlowdetailPage(pageBean ,remoteDiningListVO);
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
     * 异地就餐详情
     * @param
     */
    @ApiOperation(value = "异地就餐详情")
    @RequestMapping(value = "/getRemoteDiningDetail", method = RequestMethod.POST)
    public @ResponseBody
    ResultWrapper getRemoteDiningDetail(@RequestBody Map map) {
        String flowId = map.get("flowId").toString();
        List<Map<String, Object>> appTApplyCanteen = appTApplyCanteenService.getRemoteDiningDetail(flowId);
        return  new ResultWrapper().success().data(appTApplyCanteen);
    }

    /**
     * 异地就餐审批
     * @param
     */
    @ApiOperation(value = "异地就餐审批")
    @ApiImplicitParams({
            @ApiImplicitParam(name="flowId",value="单号",required=true,paramType="string"),
            @ApiImplicitParam(name="status",value="状态",required=true,paramType="string"),
            @ApiImplicitParam(name="userID",value="用户id",required=true,paramType="string"),
            @ApiImplicitParam(name="remark",value="备注",paramType="string")
    })
    @RequestMapping(value = "/approvelRemoteDining", method = RequestMethod.POST)
    public @ResponseBody
    ResultWrapper approvelRemoteDining(@RequestBody Map map) {
//        String flowId = map.get("flowId")==null?"":map.get("flowId").toString();
        String status = map.get("status")==null?"":map.get("status").toString();
        String userId = map.get("userID")==null?"":map.get("userID").toString();
        String remark = map.get("remark")==null?"":map.get("remark").toString();
        String id = map.get("id")==null?"":map.get("id").toString();
        String reason = map.get("reason")==null?"":map.get("reason").toString();
        LocalDateTime n = LocalDateTime.now();
        appTApplyCanteenService.approvelRemoteDining(status,userId,remark,n,id,reason);
        return  new ResultWrapper().success().data("操作成功！");
    }



    /**
     * 根据流程id获取流程节点
     * @param
     */
    @ApiOperation(value = "获取流程节点信息")
    @RequestMapping(value = "/getFlowDetail", method = RequestMethod.POST)
    public @ResponseBody
    ResultWrapper getFlowDetail(@RequestBody Map map) {
        String flowId = map.get("flowId").toString();
        List<Map<String, Object>> appTApplyCanteen = appTApplyCanteenService.getFlowDetail(flowId);
        return  new ResultWrapper().success().data(appTApplyCanteen);
    }

    /**
     * @Author lilong
     * @Description 查询待办信息
     * @Date 2020/11/20 10:27
     * @Param
     * @return
    **/
    @ApiOperation(value = "查询待办信息")
    @RequestMapping(value = "/getAllInfo", method = RequestMethod.POST)
    public @ResponseBody
    ResultWrapper getAllInfo(@RequestBody Map map) {
        ResultWrapper result = new ResultWrapper();
        //入参校验，可能请求参数不对
        try{
            //搜索关键字
            String keyWord = map.get("keyWord").toString();
            //流程状态，1.待审,2.通过.3.驳回,4.撤销
            //当前接口查询条件
            //1  待审批（需要自己审批的）
            //2  已提交（自己提交的所有申请）
            //3  已审批（流程已走完）
            String status = map.get("status").toString();
            String userId = map.get("userId").toString();
        }catch (Exception e){
            e.printStackTrace();
            result.message("非法入参");
            result.fail();
            return result;
        }
        PageBean<Map<String, String>> appTApplyCanteen = appTApplyCanteenService.getAllInfo(map);
        return  new ResultWrapper().success().data(appTApplyCanteen);
    }
}

