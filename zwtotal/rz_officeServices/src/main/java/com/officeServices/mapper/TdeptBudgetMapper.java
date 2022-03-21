package com.officeServices.mapper;

import com.officeServices.entity.TdeptBudget;
import com.officeServices.entity.Tevaluate;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface TdeptBudgetMapper {

    //查询预算列表
    List<Map> getBudget(@Param("isPaper")String isPaper,@Param("orgId")String orgId,
                        @Param("index")Integer index, @Param("pageSize")Integer pageSize);

    //查询预算列表数量
    Integer getBudgetCnt(@Param("isPaper")String isPaper,@Param("orgId")String orgId);

    //获取已执行的预算
    List<Map> getAmountprice(@Param("typeId")String typeId,@Param("beginDate")String beginDate,
                            @Param("endDate")String endDate,@Param("demanddepid")String demanddepid);

    //预算设置
    Integer insertBudget(TdeptBudget tdeptBudget);

    //查询申请信息
    List<Map> getBudgetInfo(@Param("isPaper")String isPaper,@Param("orgId")String orgId,
                            @Param("date")String date);

    //获取追加额度
    Map getAmount(@Param("orderid")String orderid);

    //修改部门额度
    void updateAmount(@Param("depId")String depId, @Param("date")String date,
                      @Param("money")BigDecimal money, @Param("isPaper")String isPaper);

    //用户查询申请情况
    List<Map> getSituation(@Param("typeId")String typeId,@Param("beginPersonId")String beginPersonId,
                           @Param("depId")String depId,@Param("status")String status,
                           @Param("index")Integer index, @Param("pageSize")Integer pageSize);

    //用户查询申请情况的数量
    Integer getSituationCnt(@Param("typeId")String typeId,@Param("beginPersonId")String beginPersonId,
                            @Param("depId")String depId,@Param("status")String status);

    Integer getData(@Param("typeId")String typeId,@Param("beginPersonId")String beginPersonId,
                    @Param("depId")String depId,@Param("status")String status);

    //获取评价列表
    List<Map> getPjList(@Param("typeId")String typeId,@Param("beginPersonId")String beginPersonId,
                        @Param("depId")String depId,@Param("index")Integer index,@Param("pageSize")Integer pageSize);

    //获取评价列表数量
    Integer getPjListCnt(@Param("typeId")String typeId,@Param("beginPersonId")String beginPersonId,
                         @Param("depId")String depId,@Param("status")String status);

    //查询评价物品明细
    List<Map> getPjGoods(@Param("orderId")String orderId);

    //查询物品历史评价
    Map getHistory(@Param("goodNo")String goodNo);

    //用户评价
    void evaluate(Tevaluate tevaluate);

    //修改主表状态
    void updatePjzt(@Param("orderid")String orderid);
}
