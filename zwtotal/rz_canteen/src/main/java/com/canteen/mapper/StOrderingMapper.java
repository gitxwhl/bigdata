package com.canteen.mapper;

import com.canteen.entity.StCabinet;
import com.canteen.entity.StOrderManagement;
import com.canteen.entity.StOrdering;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface StOrderingMapper {

    //线上订餐列表数量查询
    Integer getOrderListCnt(@Param("restaurant")String restaurant,@Param("orderNum")String orderNum,
                            @Param("scheduled")String scheduled, @Param("mealType")String mealType,
                            @Param("deduction")String deduction);
    //线上订餐列表查询
    List getOrderList(@Param("restaurant")String restaurant,@Param("orderNum")String orderNum,
                      @Param("scheduled")String scheduled, @Param("mealType")String mealType,
                      @Param("deduction")String deduction,@Param("index")Integer index,@Param("pageSize")Integer pageSize);

    //查询人员总数
    Integer getPersonCnt(@Param("ids")String ids);

    //查询食品分类
    List getDishInfo(@Param("orderNo")String orderNo,@Param("dishesCategory")String dishesCategory);

    //预订
    boolean addOrdering(StOrdering stOrdering);
    boolean  orderDishes(@Param("orderNo")String orderNo,@Param("dishId")Integer dishId,@Param("dishNum")String dishNum);
    boolean addOrderManagement(StOrderManagement stOrderManagement);
    //查询菜品价格
    Double getPrice(@Param("id")Integer id);

    //预订菜品详情
    Map getOrderInfo(@Param("orderNumber")String orderNumber);
    //获取人员名单
    List getPersonName(@Param("ids")String ids);

    //取消预订
    void cancelOrder(@Param("id")Integer id);
    //通过id查询订单号
    String getOrderNum(@Param("id")Integer id);
    //删除已取消的订单
    void deleteOrder(@Param("orderNum")String orderNum);

    //查询菜品信息
    List getDishes(@Param("restaurant")String restaurant,@Param("date")String date,
                   @Param("mealType")String mealType,@Param("dishesCategory")String dishesCategory);


    //-----------------------------------------------打包服务-------------------------------------------------------
    //打包服务列表数量
    Integer getPackageServiceCnt(@Param("restaurant")String restaurant,@Param("orderNum")String orderNum,
                                 @Param("name")String name, @Param("scheduled")String scheduled,
                                 @Param("mealType")String mealType,@Param("type")String type);
    //打包服务列表查询
    List getPackageService(@Param("restaurant")String restaurant,@Param("orderNum")String orderNum,
                           @Param("name")String name, @Param("scheduled")String scheduled,
                           @Param("mealType")String mealType,@Param("type")String type,
                           @Param("index")Integer index,@Param("pageSize")Integer pageSize);

    //打包上柜
    void packaging(StCabinet stCabinet);

    //-----------------------------------------------打包服务-------------------------------------------------------
    //异地列表数量
    Integer distributionListCnt(@Param("restaurant")String restaurant,@Param("orderNum")String orderNum,
                                @Param("scheduled")String scheduled, @Param("mealType")String mealType);

    //异地列表查询
    List distributionList(@Param("restaurant")String restaurant,@Param("orderNum")String orderNum,
                          @Param("scheduled")String scheduled, @Param("mealType")String mealType,
                          @Param("index")Integer index,@Param("pageSize")Integer pageSize);
}
