package com.officeServices.service;

import com.officeServices.entity.Tstock;
import com.officeServices.entity.TstockPlan;
import com.officeServices.entity.GoodTypeTree;

import java.util.List;
import java.util.Map;

public interface TstockService {

    //---------------------------------------------库存查询-------------------------------------------------------------

    //库存查询
    Object findTstock(Map<String, Object> map);

    //新建物品
    Object addGood(Tstock tstock);

    //修改物品
    Object updateGood(Tstock tstock);

    //库存查询-下拉框
    Object getTypeId();

    //---------------------------------------------采购计划-------------------------------------------------------------
    //发起采购申请
    Object purchaseApply(Map<String, Object> map);

    //发起采购申请--下拉框
    Object getData();

    //用户查询提交的申请
    Object getSave(Map<String, Object> map);

    //编辑保存的申请
    Object updateSave(Map<String, Object> map);

    //删除保存的申请
    Object deleteSava(TstockPlan tstockPlan);

    //审批人查询采购列表
    Object getListBySp(Map<String, Object> map);

    //审批人审批
    Object updateBySp(TstockPlan tstockPlan);

    //---------------------------------------------入库管理--------------------------------------------------------------
    //查询待入库列表
    Object getDrkList(Map<String, Object> map);

    //仓库管理员入库
    Object storeManagerInStore(Map<String, Object> map);

    //仓库管理员上架
    Object upToStock(Map<String, Object> map);

    //---------------------------------------------出库管理--------------------------------------------------------------
    //查询出库管理列表
    Object getCkList(Map<String, Object> map);

    //---------------------------------------------库存统计--------------------------------------------------------------
    //库存统计
    Object inventoryStatistics();

    //入库情况
    Object inStock(Map<String, Object> map);

    //出库情况
    Object outStock(Map<String, Object> map);

    //出库金额统计
    Object outStockPrice(Map<String, Object> map);

    /**
     * 物品类别查询
     * @param
     * @return
     */
    List<GoodTypeTree> findgoodTypeTree();
}
