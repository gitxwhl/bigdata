package com.canteen.mapper;

import com.canteen.entity.StIntelligenceMeals;
import com.canteen.entity.StIntelligenceMealsVo;
import com.canteen.entity.StMydishes;
import com.canteen.entity.StfoodManagement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;

import java.util.List;
import java.util.Map;

@Mapper
public interface StIntelligenceMealMapper {
    //手动添加--菜品查询
    List<Map> SelectMyFood(@Param("restaurant") String restaurant, @Param("meals") String meals, @Param("dishescategory")String dishescategory, @Param("name") String name);

   //一键排餐
    void deleteIntelligence();

   List SelectMyFoodByRestaurant(@Param("restaurant") String restaurant,@Param("meals") String meals,@Param("dishescategory") String dishescategory);

   //一键重排--查id
   List queryIds(@Param("restaurant") String restaurant,@Param("date")String date,@Param("meals") String meals);
    void deleteIntelligenceByMeals(@Param("id") int id);

   //删除菜品
   void DeleteMyFoodByID(@Param("id") Integer id);

   //一键排餐的菜品添加时间戳
    void insertDate(@Param("id") int id,@Param("date") String date);
   String selectByDate(@Param("id") int id,@Param("date") String date);

   //一键导出排餐列表
   List<StIntelligenceMealsVo> SelectMyBreakFoodByDate (@Param("restaurant") String restaurant,@Param("date")String date);
    List<StIntelligenceMealsVo> SelectMyLunchFoodByDate (@Param("restaurant") String restaurant,@Param("date")String date);
    List<StIntelligenceMealsVo> SelectMyDinnerFoodByDate (@Param("restaurant") String restaurant,@Param("date")String date);

    //初始化页面查询
    List<StIntelligenceMealsVo> SelectByRestaurant(@Param("restaurant") String restaurant,@Param("date") String date);

    //菜品替换--查询排餐菜品种类
   List selectCategory(@Param("restaurant") String restaurant,@Param("date") String date,
                       @Param("meals") String meals);

   //查询餐厅关联的菜品种类
    List selectCategoryOfRestaurant(@Param("restaurant") String restaurant);

    //删除不是该餐厅关联的菜品
    void deleteCategory(@Param("id") Integer id);

    //查询该餐厅关联的种类的菜品并加入排餐中
    List selectDish(@Param("restaurant") String restaurant,@Param("category") String category,
                    @Param("dishescategory") String dishescategory,@Param("meals") String meals);
}
