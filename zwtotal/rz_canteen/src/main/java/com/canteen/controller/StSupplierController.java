package com.canteen.controller;

import com.alibaba.fastjson.JSONObject;
import com.canteen.entity.StBlacklist;
import com.canteen.entity.StPenaltyList;
import com.canteen.entity.StSupplier;
import com.canteen.entity.StSupplierScore;
import com.canteen.service.StSupplierService;
import com.canteen.service.impl.StSupplierServiceImpl;
import com.canteen.utils.MySFTP;
import com.canteen.utils.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/StSupplier.do")
@Slf4j
@CrossOrigin
public class StSupplierController {

    @Autowired
    private StSupplierService stSupplierService;

    //供应商列表
    @RequestMapping(value = "/getSupplier", method = RequestMethod.POST)
    private Object getSupplier(HttpServletRequest request, @RequestBody String param){
        try {
            return stSupplierService.getSupplier(param);
        }catch (Exception e){
            e.printStackTrace();
            return "获取供应商列表失败";
        }
    }

    //导出供应商
    @RequestMapping(value = "/exportSupplier", method = RequestMethod.POST)
    private Object exportSupplier(HttpServletResponse response, @RequestBody Map<String,Object> map){
        ResultMessage resultMessage = new ResultMessage();
        try {
            resultMessage.setMessage(stSupplierService.exportSupplier(response, map).toString());
        }catch (Exception e){
            e.printStackTrace();
            resultMessage.setMessage("导出失败");
        }
        return  resultMessage;
    }


    //新增准入供应商
    @RequestMapping(value = "/addSupplier", method = RequestMethod.POST)
    private Object addSupplier(@RequestBody String stSupplier){
        try {
            return stSupplierService.addSupplier(stSupplier);
        }catch (Exception e){
            e.printStackTrace();
            List<List> list = StSupplierServiceImpl.fileList;
            for (List<String> s : list) {
                for (String s1 : s) {
                    MySFTP.delete(s1);
                }
            }
            return "添加失败";
        }
    }

    //采购食材详情
    @RequestMapping(value = "/getIngredient", method = RequestMethod.POST)
    private Object getIngredient(@RequestBody Map<String,Object> map){
        ResultMessage resultMessage = new ResultMessage();
        try {
            resultMessage.setData(stSupplierService.getIngredient(map));
        }catch (Exception e){
            e.printStackTrace();
            resultMessage.setMessage("查看采购食材详情失败");
        }
        return resultMessage;
    }

    //添加食材
    @RequestMapping(value = "/addIngredient", method = RequestMethod.POST)
    private Object addIngredient(@RequestBody Map<String,Object> map){
        ResultMessage resultMessage = new ResultMessage();
        try {
            resultMessage.setMessage("添加成功");
        }catch (Exception e){
            e.printStackTrace();
            resultMessage.setMessage("添加失败");
        }
        return resultMessage;
    }

    /*//编辑供应商
    @RequestMapping(value = "/updateSupplier", method = RequestMethod.POST)
    private Object updateSupplier(HttpServletRequest request, @RequestBody String stSupplier){
        try {
            return stSupplierService.updateSupplier(stSupplier);
        }catch (Exception e){
            e.printStackTrace();
            List<String> list = MySFTP.newFileNameList;
            for (String s : list) {
                MySFTP.delete(s);
            }
            return "变更失败";
        }
    }

    //查询供应商遴选列表
    @RequestMapping(value = "/getChooseList", method = RequestMethod.POST)
    private Object getChooseList(HttpServletRequest request, @RequestBody String param){
        try {
            return stSupplierService.getChooseList(param);
        }catch (Exception e){
            e.printStackTrace();
            return "查询供应商遴选列表失败";
        }
    }

    //供应商遴选
    @RequestMapping(value = "/choose", method = RequestMethod.POST)
    private Object choose(HttpServletRequest request, @RequestBody StSupplier stSupplier){
        try {
            return stSupplierService.choose(stSupplier);
        }catch (Exception e){
            e.printStackTrace();
            return "供应商遴选失败";
        }
    }

    //获取供应商评价
    @RequestMapping(value = "/getEvaluate", method = RequestMethod.POST)
    private Object getEvaluate(HttpServletRequest request, @RequestBody String param){
        try {
            return stSupplierService.getEvaluate(param);
        }catch (Exception e){
            e.printStackTrace();
            return "获取供应商评价失败";
        }
    }

    //添加评分
    @RequestMapping(value = "/addScore", method = RequestMethod.POST)
    private Object addScore(@RequestBody String stSupplier){
        try {
            return stSupplierService.addScore(stSupplier);
        }catch (Exception e){
            e.printStackTrace();
            List<String> list = MySFTP.newFileNameList;
            for (String s : list) {
                MySFTP.delete(s);
            }
            return "添加评分失败";
        }
    }

    //惩罚详情
    @RequestMapping(value = "/getPunishment", method = RequestMethod.POST)
    private Object getPunishment(HttpServletRequest request, @RequestBody String stSupplier){
        try {
            return stSupplierService.getPunishment(stSupplier);
        }catch (Exception e){
            e.printStackTrace();
            return "获取惩罚详情失败";
        }
    }

    //添加惩罚
    @RequestMapping(value = "/addPunishment", method = RequestMethod.POST)
    private Object addPunishment(@RequestBody String stSupplier){
        try {
            return stSupplierService.addPunishment(stSupplier);
        }catch (Exception e){
            e.printStackTrace();
            List<String> list = MySFTP.newFileNameList;
            for (String s : list) {
                MySFTP.delete(s);
            }
            return "添加惩罚失败";
        }
    }

    //黑名单详情
    @RequestMapping(value = "/getBlacklist", method = RequestMethod.POST)
    private Object getBlacklist(HttpServletRequest request, @RequestBody String stSupplier){
        try {
            return stSupplierService.getBlacklist(stSupplier);
        }catch (Exception e){
            e.printStackTrace();
            return "获取黑名单详情失败";
        }
    }

    //添加黑名单
    @RequestMapping(value = "/addBlacklist", method = RequestMethod.POST)
    private Object addBlacklist(@RequestBody String stSupplier){
        try {
            return stSupplierService.addBlacklist(stSupplier);
        }catch (Exception e){
            e.printStackTrace();
            List<String> list = MySFTP.newFileNameList;
            for (String s : list) {
              MySFTP.delete(s);
            }
            return "添加黑名单失败";
        }
    }*/
}
