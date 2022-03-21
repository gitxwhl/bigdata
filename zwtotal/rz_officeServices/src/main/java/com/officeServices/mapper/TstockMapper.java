package com.officeServices.mapper;

import com.officeServices.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TstockMapper {

    //-------------------------------------------库存查询-----------------------------------------------------
    //库存查询
    List<Map> findGood(@Param("goodName")String goodName,@Param("type")String type,
                       @Param("index")Integer index, @Param("pageSize")Integer pageSize);

    //库存数量查询
    Integer findGoodCnt(@Param("goodName")String goodName,@Param("type")String type);

    //新建物品
    void addGood(Tstock tstock);

    //修改物品
    void updateGood(Tstock tstock);

    //-------------------------------------------采购计划-----------------------------------------------------
    //提交采购计划
    void insertPlan(TstockPlan tstockPlan);

    //采购物品明细
    void insertPlanDetail(TstockPlanDetail tstockPlanDetail);

    //发起采购申请—下拉框
    List<Map> getData();


    //用户查询提交的申请
    List<Map> getListByUser(@Param("beginPersonId")String beginPersonId,@Param("status")String status,
                            @Param("index")Integer index, @Param("pageSize")Integer pageSize);

    //用户查询提交的申请数量
    Integer getListByUserCnt(@Param("beginPersonId")String beginPersonId,@Param("status")String status);

    //用户删除保存的申请
    void deleteSave(TstockPlan tstockPlan);

    //审批人查询采购计划列表
    List<Map> getListBySp(@Param("endPersonId")String endPersonId,@Param("depId")String depId,
                          @Param("index")Integer index, @Param("pageSize")Integer pageSize);

    //审批人查询采购计划列表数量
    Integer getListBySpCnt(@Param("endPersonId")String endPersonId,@Param("depId")String depId,
                           @Param("status")String status);

    //通过申请编号查询物品明细
    List<Map> getGoodsByNo(@Param("applyNo")String applyNo);

    //审批人审批
    void updateBySp(TstockPlan tstockPlan);

    //-----------------------------------------------入库管理------------------------------------------------------
    //入库列表
    List<Map> getDckList(@Param("depId")String depId,@Param("index")Integer index,
                         @Param("pageSize")Integer pageSize);

    //入库列表数量
    Integer getDckListCnt(@Param("depId")String depId);

    //添加主表记录
    void insertRecordMain(TstockPlanRecordMain tstockPlanRecordMain);

    //添加物品记录
    void insertRecord(TstockPlanRecord tstockPlanRecord);

    //通过id查询物品信息
    Map getGoodInfoById(@Param("id")String id);

    //查询入库/上架记录
    List<Map> selectRcRecord(@Param("applyNo")String applyNo,@Param("goodId")String goodNo,
                             @Param("isUp")String isUp);

    //查询入库/上架数量
    Integer getAmount(@Param("applyNo")String applyNo,@Param("goodId")String goodNo,
                      @Param("isUp")String isUp);

    //修改库存待入库数量
    void updateInstore(Tstock tstock);

    //上架后修改库存信息
    void updateStock(Tstock tstock);

    //-----------------------------------------------出库管理------------------------------------------------------
    //出库列表
    List<Map> getCkList(@Param("depId")String depId,@Param("index")Integer index,
                        @Param("pageSize")Integer pageSize);

    //出库列表数量
    Integer getCkListCnt(@Param("depId")String depId);

    //-----------------------------------------------库存统计------------------------------------------------------

    //待入库  待上架  待出库  告警数
    Map<String,Integer> getStockInfo();

    //物品库存种类
    Integer getStockKind(@Param("date")String date);

    //物品库存数量
    Integer getStockCnt();

    //上架/入库数量
    Integer addStockCnt(@Param("isUp")String isUp,@Param("operateTime")String operateTime);

    //上架/入库种类
    Integer addStockKind(@Param("isUp")String isUp,@Param("operateTime")String operateTime);

    //出库种类
    Integer outStockKind(@Param("operateTime")String operateTime);

    //出库数量
    Integer outStockCnt(@Param("operateTime")String operateTime);

    //出库总金额统计
    Double outStockPrice(@Param("typeid")String typeid,@Param("beginDate")String beginDate,
                         @Param("endDate")String endDate);

    //各部门本季度领用情况
    List<Map> getReceiveInfo();

    /**
     * 物品类别查询
     * @param
     * @return
     */
    List<GoodTypeTree> findgoodTypeTree();

}
