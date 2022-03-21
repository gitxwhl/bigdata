package com.canteen.mapper;

import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface StBusinessDataMapper {

    //查询订单编号和这笔订单的取餐方式
    List selectOrderAndWay(@Param("restaurant") String restaurant, @Param("startData") String startData,
                          @Param("endData") String endData);

    //拿到订单编号后去查询对应的菜品id
    List selectIdByOrderNumber(@Param("ordernumber") String ordernumber);

    //根据菜品id去取对应的菜品详细信息
    Map selectDishById(@Param("id") Integer id);

    //查询此id在营业数据表中是否存在
    String selectNameById(@Param("id") Integer id);

    //往营业数据表中插入数据
    void insertData(@Param("meals") String meals,@Param("name") String name,
                    @Param("dishescategory") String dishescategory,@Param("taste") String taste,
                    @Param("category") String category,@Param("id") Integer id,
                    @Param("scheduled") String scheduled);
    //刷脸、刷卡、客饭单取餐数加一
    void updateFace(@Param("id") Integer id);
    void updateCard(@Param("id") Integer id);
    void updateGuest(@Param("id") Integer id);

    //每次查询营养数据前先清空营业数据表
    void deleteDate();

    //查询营业数据总量
    Integer selectDataCount();

    //查询每条营业数据
    List selectStBusinessData(@Param("index") int index,@Param("pageSize") int pageSize);

    //求销售，刷脸，刷卡，客饭单总量
    Map selectTotal();

    //通过id查询运营餐厅信息
    List getDecide(@Param("id")String id);

    List getOpById(@Param("parentIds")int parentIds);

    //查询全部餐厅编码
    List selectRestaurantCode();

}
