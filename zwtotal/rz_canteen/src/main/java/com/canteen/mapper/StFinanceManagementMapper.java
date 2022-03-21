package com.canteen.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface StFinanceManagementMapper {

    //就餐结算明细列表数量
    Integer detailListCnt(@Param("restaurant")String restaurant, @Param("startTime")String startTime,
                          @Param("endTime")String endTime);

    //就餐结算明细列表
    List detailList(@Param("restaurant")String restaurant, @Param("startTime")String startTime,
                    @Param("endTime")String endTime, @Param("index")Integer index,
                    @Param("pageSize")Integer pageSize);

    //线上销量top5
    List onlineTopFive(@Param("dishesCategory")String dishesCategory,@Param("restaurant")String restaurant,
                       @Param("startTime")String startTime, @Param("endTime")String endTime);

    //----------------------------------------------食堂经营成本统计----------------------------------------------------
    //食堂经营成本分析列表数量
    Integer costListCnt(@Param("restaurant")String restaurant,@Param("startTime")String startTime,
                        @Param("endTime")String endTime);
    //食堂经营成本分析列表查询
    List costList(@Param("restaurant")String restaurant,@Param("startTime")String startTime,
                  @Param("endTime")String endTime,@Param("index")Integer index,
                  @Param("pageSize")Integer pageSize,@Param("page")String page);

    //通过时间和餐别查询线上订单号
    List getOrderNo(@Param("date")String date,@Param("mealType")String mealType,
                    @Param("restaurant")String restaurant);

    //通过订单号查询销售信息
    List getSaleInfo(@Param("orderNo")String orderNo);
}
