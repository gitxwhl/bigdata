package com.officeServices.mapper;

import com.officeServices.entity.ApplyOrder;
import com.officeServices.entity.ApplyOrderApproval;
import com.officeServices.entity.ApplyOrderGood;
import com.officeServices.entity.Tstock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TapplyOrderMapper {

    //提交办公服务用品申请
    void addApplyOrder(ApplyOrder applyOrder);

    //查询当前年度预算
    List<Map<String,Object>> nowYearMoneyData(@Param("orgId")String orgId, @Param("isPaper")String isPaper);

    //管理员获取可用余额
    List<Map<String,Object>> adminUsedData(@Param("demandDepId")String demandDepId,@Param("typeId")String typeId,
                            @Param("beginDate")String beginDate,@Param("endDate")String endDate);

    //普通用户获取可用余额
    List<Map<String,Object>> normalUsedData(@Param("demandDepId")String demandDepId,@Param("typeId")String typeId,
                             @Param("beginDate")String beginDate,@Param("endDate")String endDate);

    //向t_apply_order_approvel表里插入审批记录
    void addApproval(ApplyOrderApproval applyOrderApproval);

    //向物品条目表里插入多条物品
    void addGood(ApplyOrderGood applyOrderGood);

    //审批人查询审批列表
    List<Map<String,Object>> getApprovalList(@Param("dealUserId")String dealUserId,@Param("typeId")String typeId,
                                             @Param("depId")String depId,
                                             @Param("index")Integer index, @Param("pageSize")Integer pageSize);
    //审批人查询审批列表数量
    Integer getApprovalListCnt(@Param("dealUserId")String dealUserId,@Param("typeId")String typeId,
                               @Param("depId")String depId);

    //通过订单号查询物品明细
    List<Map<String,Object>> getGoodById(@Param("orderId")String orderId);

    Integer getData(@Param("dealUserId")String dealUserId,@Param("typeId")String typeId,
                    @Param("status")String status, @Param("depId")String depId);

    //审批人审批
    void approveApply(@Param("status")String status,@Param("remark")String remark,
                      @Param("orderId")String orderId,@Param("step")String step);

    //审批通过修改存库信息
    void updatePass(Tstock tstock);

    //通过审批的列表
    /*List<Map> getPassList(@Param("typeId")String typeId, @Param("depId")String depId,
                          @Param("index")Integer index, @Param("pageSize")Integer pageSize);*/
    //通过审批的列表数量
    //Integer getPassListCnt(@Param("typeId")String typeId, @Param("depId")String depId);

    //通过物品id查询物品的当前库存
    Map getNowStore(@Param("goodId")String goodId);

    //发放库存，修改主表状态
    void updateState(@Param("orderid")String orderid);

    //获取发放记录id
    String getRecordId(@Param("orderid")String orderid);

    //发放库存，更新操作人员信息
    void updatePersonInfo(ApplyOrderApproval applyOrderApproval);

    //出库修改库存信息
    void updateNowStore(Tstock tstock);

    //查询库存物品
    List<Map> getGoodFromStock(@Param("typeid")String typeid);

    //通过id查询物品详情
    Map getGoodInfo(@Param("id")String id);

    List<Map> getNextPerson(@Param("sql")String sql);
}

