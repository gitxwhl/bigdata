package com.canteen.mapper;

import com.canteen.entity.StRestaurantinformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StRestaurantinformationMapper {

    //查询餐厅信息
    StRestaurantinformation insertMorning(@Param("restaurant") String restaurant);
    StRestaurantinformation  insertChinesefast(@Param("restaurant") String restaurant);
    StRestaurantinformation  insertSupper(@Param("restaurant") String restaurant);

    //修改
    int updateMeal(@Param("id") Integer id,
                   @Param("supplystarttime") String supplystarttime,
                   @Param("supplyendtime") String supplyendtime,
                   @Param("givestarttime") String givestarttime,
                   @Param("giveendtime") String giveendtime,
                   @Param("upperstarttime") String upperstarttime,
                   @Param("upperendtime") String upperendtime,
                   @Param("mealintroduction") String mealintroduction,
                   @Param("dictionary") String dictionary,
                   @Param("dishes") String dishes,
                   @Param("restaurant") String restaurant);
    //新增
    int insertMeal(@Param("id") Integer id,
                   @Param("supplystarttime") String supplystarttime,
                   @Param("supplyendtime") String supplyendtime,
                   @Param("givestarttime") String givestarttime,
                   @Param("giveendtime") String giveendtime,
                   @Param("upperstarttime") String upperstarttime,
                   @Param("upperendtime") String upperendtime,
                   @Param("mealintroduction") String mealintroduction,
                   @Param("dictionary") String dictionary,
                   @Param("dishes") String dishes,
                   @Param("restaurant") String restaurant);
}
