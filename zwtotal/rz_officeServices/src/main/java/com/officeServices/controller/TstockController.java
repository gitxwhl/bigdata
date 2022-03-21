package com.officeServices.controller;

import com.officeServices.entity.GoodTypeTree;
import com.officeServices.entity.Tstock;
import com.officeServices.entity.TstockPlan;
import com.officeServices.service.TstockService;
import com.officeServices.utils.BaseCode;
import com.officeServices.utils.ResultMessage;
import com.officeServices.utils.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/Tstock.do")
@Slf4j
@CrossOrigin
public class TstockController {

    @Autowired
    private TstockService tstockService;

    //------------------------------------------------库存查询----------------------------------------------------
    /**
     * 库存查询
     */
    @RequestMapping(value = "/findTstock", method = RequestMethod.POST)
    public Object findTstock(@RequestBody Map<String,Object> map){
        try {
            return tstockService.findTstock(map);
        }catch (Exception e){
            e.printStackTrace();
            return "库存查询失败";
        }
    }

    /**
     * 新建物品
     */
    @RequestMapping(value = "/addGood", method = RequestMethod.POST)
    public Object addGood(@RequestBody Tstock tstock){
        try {
            return tstockService.addGood(tstock);
        }catch (Exception e){
            e.printStackTrace();
            return "新建物品失败";
        }
    }

    /**
     * 修改物品
     */
    @RequestMapping(value = "/updateGood", method = RequestMethod.POST)
    public Object updateGood(@RequestBody Tstock tstock){
        ResultMessage resultMessage = new ResultMessage();
        try {
            resultMessage.setMessage(tstockService.updateGood(tstock).toString());
        }catch (Exception e){
            e.printStackTrace();
            resultMessage.setMessage("修改失败");
        }
        return resultMessage;
    }

    /**
     * 库存查询-下拉框
     */
    @RequestMapping(value = "/getTypeId", method = RequestMethod.GET)
    public Object getTypeId(){
        try {
            return tstockService.getTypeId();
        }catch (Exception e){
            e.printStackTrace();
            return "获取下拉框失败";
        }
    }

    //------------------------------------------------采购计划----------------------------------------------------
    /**
     * 发起采购申请
     */
    @RequestMapping(value = "/purchaseApply", method = RequestMethod.POST)
    public Object purchaseApply(@RequestBody Map<String,Object> map){
        try {
            return tstockService.purchaseApply(map);
        }catch (Exception e){
            e.printStackTrace();
            return "操作失败";
        }
    }

    /**
     * 发起采购申请--下拉框
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public Object getData(){
        try {
            return tstockService.getData();
        }catch (Exception e){
            e.printStackTrace();
            return "获取下拉框失败";
        }
    }

    /**
     * 用户查询提交的申请
     */
    @RequestMapping(value = "/getSave", method = RequestMethod.POST)
    public Object getSave(@RequestBody Map<String,Object> map){
        try {
            return tstockService.getSave(map);
        }catch (Exception e){
            e.printStackTrace();
            return "查询提交的申请失败";
        }
    }

    /**
     * 删除已保存的申请
     */
    @RequestMapping(value = "/deleteSava", method = RequestMethod.POST)
    public Object deleteSava(@RequestBody TstockPlan tstockPlan){
        try {
            return tstockService.deleteSava(tstockPlan);
        }catch (Exception e){
            e.printStackTrace();
            return "删除失败";
        }
    }

    /**
     * 审批人查询采购列表
     */
    @RequestMapping(value = "/getListBySp", method = RequestMethod.POST)
    public Object getListBySp(@RequestBody Map<String,Object> map){
        try {
            return tstockService.getListBySp(map);
        }catch (Exception e){
            e.printStackTrace();
            return "查询采购列表失败";
        }
    }

    /**
     * 审批人审批
     */
    @RequestMapping(value = "/updateBySp", method = RequestMethod.POST)
    public Object updateBySp(@RequestBody TstockPlan tstockPlan){
        try {
            return tstockService.updateBySp(tstockPlan);
        }catch (Exception e){
            e.printStackTrace();
            return "审批异常";
        }
    }

    //------------------------------------------------入库管理----------------------------------------------------
    /**
     * 查询待入库列表
     */
    @RequestMapping(value = "/getDrkList", method = RequestMethod.POST)
    public Object getDrkList(@RequestBody Map<String,Object> map){
        try {
            return tstockService.getDrkList(map);
        }catch (Exception e){
            e.printStackTrace();
            return "查询待入库列表异常";
        }
    }

    /**
     * 仓库管理员入库
     */
    @RequestMapping(value = "/storeManagerInStore", method = RequestMethod.POST)
    public Object storeManagerInStore(@RequestBody Map<String,Object> map){
        try {
            return tstockService.storeManagerInStore(map);
        }catch (Exception e){
            e.printStackTrace();
            return "入库失败";
        }
    }

    /**
     * 仓库管理员上架
     */
    @RequestMapping(value = "/upToStock", method = RequestMethod.POST)
    public Object upToStock(@RequestBody Map<String,Object> map){
        try {
            return tstockService.upToStock(map);
        }catch (Exception e){
            e.printStackTrace();
            return "上架失败";
        }
    }

    //------------------------------------------------出库管理----------------------------------------------------
    /**
     * 查询出库管理列表
     */
    @RequestMapping(value = "/getCkList", method = RequestMethod.POST)
    public Object getCkList(@RequestBody Map<String,Object> map){
        try {
            return tstockService.getCkList(map);
        }catch (Exception e){
            e.printStackTrace();
            return "查询出库管理列表异常";
        }
    }

    //------------------------------------------------库存统计----------------------------------------------------
    /**
     * 库存统计
     */
    @RequestMapping(value = "/inventoryStatistics", method = RequestMethod.POST)
    public Object inventoryStatistics(){
        try {
            return tstockService.inventoryStatistics();
        }catch (Exception e){
            e.printStackTrace();
            return "查询库存统计异常";
        }
    }

    /**
     * 入库情况
     */
    @RequestMapping(value = "/inStock", method = RequestMethod.POST)
    public Object inStock(@RequestBody Map<String,Object> map){
        try {
            return tstockService.inStock(map);
        }catch (Exception e){
            e.printStackTrace();
            return "查询入库情况异常";
        }
    }

    /**
     * 出库情况
     */
    @RequestMapping(value = "/outStock", method = RequestMethod.POST)
    public Object outStock(@RequestBody Map<String,Object> map){
        try {
            return tstockService.outStock(map);
        }catch (Exception e){
            e.printStackTrace();
            return "查询出库情况异常";
        }
    }

    /**
     * 出库金额统计
     */
    @RequestMapping(value = "/outStockPrice", method = RequestMethod.POST)
    public Object outStockPrice(@RequestBody Map<String,Object> map){
        try {
            return tstockService.outStockPrice(map);
        }catch (Exception e){
            e.printStackTrace();
            return "出库金额统计异常";
        }
    }

    /**
     * 物品类别查询
     */
    @RequestMapping("/findgoodTypeTree")
    @ResponseBody
    public Results<List<GoodTypeTree>> findgoodTypeTree(){
        List<GoodTypeTree> goodTypeTree = tstockService.findgoodTypeTree();
        return new Results<List<GoodTypeTree>>(BaseCode.BaseResultCode.SUCSESS,"",goodTypeTree);
    }

}
