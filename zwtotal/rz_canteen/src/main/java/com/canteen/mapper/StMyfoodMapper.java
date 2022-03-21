package com.canteen.mapper;

import com.canteen.entity.StMydishes;
import com.canteen.entity.StfoodManagement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface StMyfoodMapper {

    //获取菜品数量
    Integer getTotalSize(@Param("restaurant") String restaurant,@Param("name") String name,
                         @Param("category") String category);
    //获取不同运维餐厅下所有的菜品列表
    List selectByRestaurant(@Param("restaurant") String restaurant,@Param("name") String name,
                            @Param("category") String category,
                            @Param("startIndex")int startIndex,@Param("pageSize")int pageSize);

    //在全量库中根据菜品名称查询菜品信息
    Integer getTotal(@Param("name") String name,
                     @Param("category") String category);
    List selectNameByFoodManage(@Param("name") String name,
                                                  @Param("category") String category,
    @Param("startIndex")int startIndex,@Param("pageSize")int pageSize);

    //菜品营养分析
    List CalculationNutritional(String dishcode);

    //根据菜品id删除菜品
    void deleteMyFoodById(int id);

    //从全量库中查询菜品
    StMydishes selectManageFood(String id);

    String selectDishcode(@Param("id") String id,@Param("restaurant") String restaurant);
    //添加菜品
    void insertMyFood(StMydishes stMydishes);

    //菜类查询
    List<Map> selectdish();

    //--------------------------------------查询菜品-----------------------------------------------
    //查询菜品数量
    Integer selectDishCnt(@Param("restaurantName") String restaurantName,@Param("name") String name,
                          @Param("category") String category,@Param("dishesCategory") String dishesCategory,
                          @Param("time") String time,@Param("meals")String meals);

    List selectDish(@Param("restaurantName") String restaurantName,@Param("name") String name,
                    @Param("category") String category,@Param("dishesCategory") String dishesCategory,
                    @Param("time") String time,@Param("meals")String meals,
                    @Param("index") int index,@Param("pageSize") int pageSize);
}

