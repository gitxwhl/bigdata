package com.canteen.mapper;

import com.canteen.entity.StCabinet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StCabinetMapper {

    //餐柜管理列表数量
    Integer getCabinetCnt(@Param("number")String number,@Param("name")String name,
                          @Param("state")String state,@Param("restaurant")String restaurant);
    //餐柜管理列表
    List getCabinet(@Param("number")String number, @Param("name")String name,
                    @Param("state")String state, @Param("restaurant")String restaurant,
                    @Param("index")Integer index,@Param("pageSize")Integer pageSize);

    //餐柜上货,更换订单
    Integer addOrder(StCabinet stCabinet);

    //清空餐柜
    Integer emptyCabinet();

    //获取上架人员
    List getPerson();

    //查询空餐柜
    List getEmptyCabinet();

    //查询非空餐柜
    List getNoEmptyCabinet();

    //查询餐柜订单
    List getCabinOrder();

    //通过餐柜id查询订单号
    String getOrderNumById(@Param("id")Integer id);
}
