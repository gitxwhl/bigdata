package com.canteen.controller;

import com.alibaba.fastjson.JSONObject;
import com.canteen.service.StDishesAnalysisService;
import com.canteen.utils.PageBean;
import com.canteen.utils.Result;
import javafx.scene.input.DataFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/StDishesAnalysis.do")
@Slf4j
@CrossOrigin
public class StDishesAnalysisController {

    @Autowired
    StDishesAnalysisService stDishesAnalysisService;

    /**
     * 菜品餐厅评价次数查询，菜品整体评价查询
     * @param request
     * @param param
     * @return
     */

    @RequestMapping(value = "/selectAnalysisOfMyFood",method = RequestMethod.POST)
    private PageBean selectAnalysisOfMyFood(HttpServletRequest request, @RequestBody String param){
        return stDishesAnalysisService.selectAnalysisOfMyFoods(param);
    }

    /**
     * 用户评价回复
     * @param request
     * @param param
     *
     */
    @RequestMapping(value = "/replay",method = RequestMethod.POST)
    private Result replay(HttpServletRequest request, @RequestBody String param){
        Result result = new Result();
        try {
            JSONObject paramDate = JSONObject.parseObject(param);
            String replay = paramDate.getString("replay");
            int id = paramDate.getIntValue("id");
            Date d = new Date();
            SimpleDateFormat DateFormate = new SimpleDateFormat("yyyy-MM-dd");
            String replaydate = DateFormate.format(d);
            stDishesAnalysisService.replay(replay,id,replaydate);
            result.setMsg("用户评价回复成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setMsg("用户评价回复失败");
        }

        return result;
    }

    /**
     * 修改用户评论
     * @param request
     * @param param
     * @param
     */
    @RequestMapping(value = "/updateReplay",method = RequestMethod.POST)
    private Result updateReplay(HttpServletRequest request, @RequestBody String param){
        Result result = new Result();
        try {
            stDishesAnalysisService.updateReplay(param);
            result.setMsg("修改成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setMsg("修改失败");
        }

        return result;
    }

    /**
     * 删除用户评论
     * @param request
     * @param param
     */
    @RequestMapping(value = "/deleteReplay",method = RequestMethod.POST)
    private Result deleteReplay(HttpServletRequest request,@RequestBody String param){
        Result result = new Result();
        try {
            JSONObject paramDate = JSONObject.parseObject(param);
            int id = paramDate.getIntValue("id");
            stDishesAnalysisService.deleteReplay(id);
            result.setMsg("删除评论成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setMsg("删除图片失败");
        }
        return result;
    }

    /**
     * 根据关键字筛选评论
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "FilterComments",method = RequestMethod.POST)
    private List FilterComments(HttpServletRequest request,@RequestBody String param){

return stDishesAnalysisService.FilterComments(param);
    }

    /**
     * 获取排餐信息
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/selectIntelligenceMeals",method = RequestMethod.POST)
    private List selectIntelligenceMeals(HttpServletRequest request,@RequestBody String param){
      return   stDishesAnalysisService.selectIntelligenceMeals(param);
    }


    // ------------------------------------------用户评价查询------------------

    /**
     * 用户评价查询
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/selectUserComment",method = RequestMethod.POST)
    private PageBean selectUserComment(HttpServletRequest request, @RequestBody String param){
return stDishesAnalysisService.selectUserComment(param);
    }

    /**
     * 用户评论提交
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = "postComment",method = RequestMethod.POST)
    private Result postComment(HttpServletRequest request,@RequestBody String param){
        Result result = new Result();
        try {
            stDishesAnalysisService.postComment(param);
            result.setMsg("评论成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setMsg("评论失败");
        }
        return result;
    }

}
