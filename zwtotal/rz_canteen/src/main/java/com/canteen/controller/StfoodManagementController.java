package com.canteen.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.canteen.entity.bo.FoodManagement;
import com.canteen.service.StfoodManagementService;
import com.canteen.utils.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/StfoodManagement.do")
@Slf4j
@CrossOrigin
public class StfoodManagementController {

    @Autowired
    StfoodManagementService stfoodManagementService;

    //查询可用食材
    @RequestMapping(value = "/getIngredient", method = RequestMethod.GET)
    private Object getIngredient(){
        ResultMessage resultMessage = new ResultMessage();
        try {
            resultMessage.setData(stfoodManagementService.getIngredient());
        }catch (Exception e){
            e.printStackTrace();
            resultMessage.setMessage("查询可用食材失败");
        }
        return resultMessage;
    }

    //根据食材id查询营养成分
    @RequestMapping(value = "/getNutritionById", method = RequestMethod.POST)
    private Object getNutritionById(@RequestBody Map<String,Object> map){
        ResultMessage resultMessage = new ResultMessage();
        try {
            resultMessage.setData(stfoodManagementService.getNutritionById(map));
        }catch (Exception e){
            e.printStackTrace();
            resultMessage.setMessage("查询营养成分失败");
        }
        return resultMessage;
    }

    //添加菜品
    @RequestMapping(value = "/insertFood", method = RequestMethod.POST)
    private Object insertFood(@RequestBody FoodManagement foodManagement){
        ResultMessage resultMessage = new ResultMessage();
        try {
            resultMessage.setMessage(stfoodManagementService.insertFood(foodManagement));
        }catch (Exception e){
            e.printStackTrace();
            resultMessage.setMessage("新增失败");
        }
        return resultMessage;
    }

    //查询关联属性（下拉框数据）
    @RequestMapping(value = "/getRelevance", method = RequestMethod.GET)
    private Object getRelevance(){
        ResultMessage resultMessage = new ResultMessage();
        try {
            resultMessage.setData(stfoodManagementService.getRelevance());
        }catch (Exception e){
            e.printStackTrace();
            resultMessage.setMessage("获取下拉框数据失败");
        }
        return resultMessage;
    }

    //菜品查询
    @RequestMapping(value = "/getFood", method = RequestMethod.POST)
    private Object getFood(@RequestBody Map<String,Object> map){
        ResultMessage resultMessage = new ResultMessage();
        try {
            resultMessage.setData(stfoodManagementService.getFood(map));
        }catch (Exception e){
            e.printStackTrace();
            resultMessage.setMessage("菜品查询失败");
        }
        return resultMessage;
    }

    //测试图片上传
    @RequestMapping(value = "/imgUploading", method = RequestMethod.POST)
    private Object imgUploading(HttpServletRequest request,HttpServletResponse response){
        return stfoodManagementService.imgUploading(request,response);
    }

    //测试排序
    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    private Object sort(HttpServletRequest request,@RequestBody String param){
        JSONObject rowData = JSONObject.parseObject(param);
        JSONArray array = rowData.getJSONArray("array");
        return stfoodManagementService.sort(array);
    }
}
