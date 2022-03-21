package com.canteen.mapper;

import com.canteen.entity.StDishevaluation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface StDishesAnalysisMapper {
    //获取一段时间评价总数
    Integer getTotals(@Param("restaurant") String restaurant,@Param("strDate") String strDate,
                      @Param("endDate") String endDate,@Param("name") String name,
                      @Param("filtercomments") String filtercomments);
    //菜品满意程度查询
    List selectAnalysisOfMyFood();
// 每次获取不同时段菜品评价数据量时先删除上次计算数据
    void deleteScore();
    //根据id查询此id是否在score中有记录
    String selectById(@Param("id") Integer id);
    //往score表中插入id
    void insertId(@Param("id") Integer id);
    //往指定评分等级更新数据
    void updateDishevaluation(@Param("id") Integer id);
    void updateSatisfied(@Param("id") Integer id);
    void updateCommonly(@Param("id") Integer id);
    void updateDissatisfied(@Param("id") Integer id);
    void updateVerydissatisfied(@Param("id") Integer id);

    //根据指定日期查询菜品ids
    List selectIds(@Param("restaurant") String restaurant,@Param("strDate") String strDate,
                        @Param("endDate") String endDate,@Param("name") String name,
                        @Param("startIndex")int startIndex,@Param("pageSize")int pageSize,
                        @Param("filtercomments") String filtercomments);

    //拿到评价id去查询对应的菜品评分id，和评分
List selectScore(@Param("id") Integer id);

    //餐馆评价查询
    List<Map> selectAnalysisOfRestaurant(@Param("restaurant") String restaurant,@Param("strDate") String strDate,
                                         @Param("endDate") String endDate,@Param("name") String name,
                                         @Param("filtercomments") String filtercomments);

    //菜品整体评价查询
    List selectAnalysisOfMyFoods(@Param("restaurant") String restaurant,
                                      @Param("strDate") String strDate,
                                      @Param("endDate") String endDate,
                                      @Param("name") String name,
                                 @Param("filtercomments") String filtercomments,
                                      @Param("startIndex")int startIndex,
                                 @Param("pageSize")int pageSize);
    //根据评论id查询各菜品评分
    List selectDishesScore(@Param("id") Integer id);
    //给菜品评价加上标签
    //菜品整体评价查询
    List setAnalysisOfMyFoods(@Param("restaurant") String restaurant,
                                 @Param("strDate") String strDate,
                                 @Param("endDate") String endDate
                                 );

    //用户评论回复
    void replay(@Param("replay") String replay,@Param("id") Integer id,
                @Param("replaydate") String replaydate);
    //修改用户评论
    void updateReplay(@Param("replay") String replay,@Param("id") Integer id,@Param("environment") Integer environment,
                      @Param("overallevaluation") Integer overallevaluation,@Param("canteenservice") Integer canteenservice);
    //修改菜品评分
    void updateScore(@Param("id") Integer id,@Param("score") Integer score);
    //删除用户评论
    void deleteReplay(@Param("id") Integer id);
    void deleteReplayOfDis(@Param("id") Integer id);
    //根据评论id修改图片地址字符串
   void updateImages(@Param("id") Integer id,@Param("images") String images);
    //根据关键字筛选评论
    List<Map> FilterComments(@Param("filtercomments") String filtercomments);
    //给菜品添加标签
    void insertFilterComments(@Param("id") Integer id,
                              @Param("filtercomments") String filtercomments);
    //查询排餐信息
    List selectIntelligenceMeals(@Param("restaurant") String restaurant,@Param("date") String date);
    //查询回复内容
    List selectReplay(@Param("id") Integer id);

    //用户评论提交

    //图片和评论内容存库
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
            void saveComment(StDishevaluation stDishevaluation);
    //菜品评分存库
   void saveScore(@Param("id") Integer id,@Param("score") Integer score,
                  @Param("name") String name,@Param("disid") Integer disid);
}
