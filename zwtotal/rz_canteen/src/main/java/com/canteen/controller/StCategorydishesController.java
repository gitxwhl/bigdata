package com.canteen.controller;
import com.canteen.constant.BaseCode;
import com.canteen.entity.StCategorydishes;
import com.canteen.service.StCategorydishesService;
import com.canteen.utils.*;
import com.google.gson.reflect.TypeToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(tags = {"StCategorydishesController"}, description = "菜单类别管理")
@RestController
@RequestMapping("/StCategorydishes.do")
@Slf4j
@CrossOrigin
public class StCategorydishesController {
    @Autowired
    private StCategorydishesService stCategorydishesService;
    /**
     * 获取菜品类别列表
     * @param
     * @return
     */
    @ApiOperation(value = "菜单类别树", notes = "菜单类别查询")
    @GetMapping("/getStCategorydishes")
    public Results<List<StCategorydishes>> getStCategorydishes(){
       /* List<StCategorydishes> StCategorydishesList = new ArrayList<>();*/
        //读取树形图数据 修改循环bug,把业务逻辑挪到service
        List<StCategorydishes> stCategorydishesList= stCategorydishesService.findStCategorydishes();
       /* Map<Integer,StCategorydishes> stCategorydishesMap = new HashMap<>();
        for (StCategorydishes sc: stCategorydishesList){
            stCategorydishesMap.put(sc.getId(),sc);
        }
        for (StCategorydishes st: stCategorydishesList){
            StCategorydishes child=st;
            if (child.getParentIds()==0) {
                StCategorydishesList.add(child);
            }else {
                StCategorydishes parent = stCategorydishesMap.get(child.getParentIds());
                parent.getSubclass().add(child);
            }
        }*/
        return new Results<List<StCategorydishes>>(BaseCode.BaseResultCode.SUCSESS,"",stCategorydishesList);
    }




    /**
     * 根据名称和编码查询获取菜品类别列表
     * @param
     * @return
     */
    //@ApiOperation(value = "根据名称和编码查询获取菜品类别列表", notes = "菜单类别查询")
   // @RequestMapping(value = "/getStCategorydishesPage", method = RequestMethod.POST)
    public void getStCategorydishesPage(HttpServletRequest req, HttpServletResponse resp, StCategorydishes stCategorydishes) throws Exception {
        HttpUtils httpUtils = new HttpUtils();
        resp.setCharacterEncoding("UTF-8");
        /**
         * TODO 这段代码不懂，先不处理
         */
        stCategorydishes = (StCategorydishes) httpUtils.getBean(req, resp, new TypeToken<StCategorydishes>() {
        }.getType());
        PageBean<StCategorydishes> pageBean = (PageBean<StCategorydishes>) httpUtils.getBean(req, resp, new TypeToken<PageBean<StCategorydishes>>() {
        }.getType());
        Result result = new Result();
        try {
            PageBean<StCategorydishes> page = stCategorydishesService.getStCategorydishesPage(pageBean, stCategorydishes.getVarietiesName(),stCategorydishes.getVarietiesCode());
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

    @ApiOperation(value = "根据名称和编码查询获取菜品类别列表", notes = "菜单类别查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称或者编码", dataType = "String",paramType="query"),
            @ApiImplicitParam(name = "id", value = "id", dataType = "int",paramType="query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "int",paramType="query",defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页长度", dataType = "int",paramType="query",defaultValue = "5")
    })

    @GetMapping("getStCategorydishesPage")
    @ResponseBody
    public Results<PageBean<StCategorydishes>>  getStCategorydishesPage(@RequestParam(value = "name" ,required = false) String name,
                                                                        @RequestParam(value = "id" ,required = false) Integer id,
                                                                        @RequestParam(value = "pageNum" ,
            defaultValue = "1") int pageNum ,@RequestParam(value = "pageSize",defaultValue = "5") int pageSize){
        PageBean<StCategorydishes> pageBean  = new PageBean<>();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        PageBean<StCategorydishes> page = stCategorydishesService.getStCategorydishesPage(pageBean, name,name,id);
        return new  Results<PageBean<StCategorydishes>>(BaseCode.BaseResultCode.SUCSESS,"查询结果",page);

    }
    /**
     * 根据id删除菜品类别
     * @param
     * @return
     */
    @ApiOperation(value = "根据id删除菜品类别", notes = "菜单类别管理")
    @RequestMapping(value = "/delStCategorydishes", method = RequestMethod.POST)
    public Results<Boolean> delStCategorydishes(@RequestBody StCategorydishes stCategorydishes, HttpServletRequest request) {
        /**
         * TODO 删除前 判断是否有之类。 没有明确表示处理； 1全部逻辑删除删除，2.修改prientid到其他地方。3.提示无法删除
         * 当前使用3
         */
      /*  boolean flag = this.stCategorydishesService.delByIds(stCategorydishes.getId(),0);
        if (flag) {
            return ResultInfo.getSuccessInfo();
        } else {
            return ResultInfo.getFailedInfo();
        }*/
        return stCategorydishesService.delByIds(stCategorydishes.getId(),0);
    }
    /**
     * 添加目录
     * TODO 。认为是菜品类别管理 添加类别 下面方法重载
     * @param
     * @return
     */
    //@RequestMapping("saveStCategorydishes")
    //@ResponseBody
    public ResultInfo saveStCategorydishes(@RequestBody StCategorydishes stCategorydishes, HttpServletRequest request, HttpServletResponse response){
        boolean flag = stCategorydishesService.saveStCategorydishesFist(stCategorydishes);
        if (flag) {
            return ResultInfo.getSuccessInfo();
        } else {
            return ResultInfo.getFailedInfo();
        }
    }

    @ApiOperation(value = "菜单类别保存", notes = "菜单类别管理")
    @PostMapping("saveStCategorydishes")
    @ResponseBody
    public  Results<Boolean>  saveStCategorydishess(@RequestBody StCategorydishes stCategorydishes, HttpServletRequest request, HttpServletResponse response){
       return    stCategorydishesService.saveStCategorydishes( stCategorydishes);
    }



    /**
     * 修改类别
     * @param
     * @return
     */
    @ApiOperation(value = "菜单类别修改", notes = "菜单类别管理")
    @PostMapping("updateStCategorydishes")
    @ResponseBody
    public Results<Boolean>  updateStCategorydishes(@RequestBody StCategorydishes stCategorydishes, HttpServletRequest request, HttpServletResponse response){
        /*boolean flag=false;
        if(stCategorydishes.getId() != null){
             flag = stCategorydishesService.updateStCategorydishesFist(stCategorydishes);
        }
        if (flag) {
            return ResultInfo.getSuccessInfo();
        } else {
            return ResultInfo.getFailedInfo();
        }*/
        if(stCategorydishes.getId() == null){
            return   new  Results<Boolean>(BaseCode.BaseResultCode.FAILE,"数据异常",false);
        }
        return stCategorydishesService.updateStCategorydishesFists(stCategorydishes);

    }

    /**
     *
     * @param id 数据id
     * @param delFlag  *删除标记  0正常  1删除  2停用
     * @return
     */
    @ApiOperation(value = "菜单类别启停", notes = "菜单类别管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delFlag", value = "删除标记  0正常  1删除  2停用", dataType = "int", paramType="query"),
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType="query")
    })
    @GetMapping("updateStCategorydishesState")
    @ResponseBody
    public Results<Boolean>  updateStCategorydishesState(@RequestParam(value = "id") int id,@RequestParam(value = "delFlag") int delFlag ){

        return stCategorydishesService.updateStCategorydishesState(id,delFlag);

    }

}
