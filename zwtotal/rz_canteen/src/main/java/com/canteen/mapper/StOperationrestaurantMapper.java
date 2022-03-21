package com.canteen.mapper;

import com.canteen.entity.StOperationrestaurant;
import com.canteen.entity.StstrategyMetering;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface StOperationrestaurantMapper {

    /**
     * 树形菜单
     * @param deFlag
     * @return
     */
    List<StOperationrestaurant> getStOperationList(@Param("deFlag") String deFlag);

    //通过id查询运营餐厅信息
    List getDecide(@Param("id")int id);

    List getOpById(@Param("parentIds")int parentIds);

    //查询策略列表
    List<StstrategyMetering> getStrategyList(@Param("startIndex") int startIndex,
                                             @Param("pageSize") int pageSize,
                                             @Param("restaurantName") String restaurantName,
                                             @Param("settlementamount") String settlementamount);

    //查询策略数量
    int getStrategyCnt(@Param("restaurantName") String restaurantName,@Param("settlementamount") String settlementamount);

    //删除策略
    int deleteStrategy(@Param("id") int id);

    //查询可关联设备
    List getEquipment();

    //查询可关联餐厅
    List getRestaurant();

    //修改策略
    int updateStrategy(StstrategyMetering ststrategyMetering);

    //新增策略
    int insertStrategy(StstrategyMetering ststrategyMetering);

    //获取字典表定义
    List dictionaries();

    //查询餐厅信息
    @Select("SELECT id,capacity,introduction,decide,parent_ids,RestaurantCode,RestaurantName,lng,lat FROM st_operationrestaurant WHERE id = #{id}")
    StOperationrestaurant selectIntroduce(@Param("id") int id);

    //单击修改餐厅信息及餐厅人数
    @Update(" UPDATE st_operationrestaurant\n" +
            "SET capacity = #{capacity},\n" +
            "\t\tintroduction = #{introduction}\n" +
            "WHERE\n" +
            "\tid = #{id}")
    int updateMealNumber(@Param("id") int id,
                         @Param("capacity") String capacity,
                         @Param("introduction") String introduction);
}
