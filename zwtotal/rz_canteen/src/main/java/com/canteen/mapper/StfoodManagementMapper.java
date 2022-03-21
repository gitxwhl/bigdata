package com.canteen.mapper;

import com.canteen.entity.StfoodManagement;
import com.canteen.entity.StnutritionalComponents;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;


@Mapper
public interface StfoodManagementMapper {

    //生成菜品编号
    String getDishCode();

    //查询可用食材
    List<Map> getIngredient();

    //根据食材id查询营养成分
    List<Map> getNutritionById(@Param("id")Integer id);

    //新增菜品
    //添加菜品表
    Integer insertFoodInfo(StfoodManagement stfoodManagement);
    //添加菜品营养成分
    Integer insertFoodElement(StnutritionalComponents components);
    //添加菜品和配料的关联数据
    Integer insertNutritionDishes(Map map);

    //菜品查询
    List<Map> getFood(@Param("msg")String msg,@Param("category")String category,
                      @Param("dishesCategory")String dishesCategory,@Param("date")String date,
                      @Param("meals")String meals,@Param("index")Integer index,
                      @Param("pageSize")Integer pageSize);

    //菜品查询
    Integer getFoodCnt(@Param("msg")String msg,@Param("category")String category,
                      @Param("dishesCategory")String dishesCategory,@Param("date")String date,
                      @Param("meals")String meals);

    //菜品营养成分分析
    List<Map> foodAnalyze(@Param("dishcode") String dishcode);

    //查询字典表定义
    List<Map> dictionaries();

    //查询菜品类别
    List<Map> getType();

    //查询菜品类别
    List<Map> selectType(@Param("type") String type);
}
