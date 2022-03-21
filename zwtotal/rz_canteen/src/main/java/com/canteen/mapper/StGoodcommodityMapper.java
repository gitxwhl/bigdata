package com.canteen.mapper;

import com.canteen.entity.StGoodcommodity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface StGoodcommodityMapper {

    int getGoodcommodityPageCount(@Param("inventory")String inventory,@Param("shelfLife")String shelfLife,@Param("price")String price,@Param("branchName")String branchName,@Param("name")String name,@Param("typeid")String typeid,@Param("restaurant")String restaurant);
    /**
     * 商品类别查询
     * @param start
     * @param pageSize
     * @return
     */
    List<StGoodcommodity> getStGoodcommodityPage(@Param("inventory")String inventory,@Param("shelfLife")String shelfLife,@Param("price")String price,@Param("branchName")String branchName,@Param("name")String name, @Param("start")int start, @Param("pageSize") int pageSize,
                                                 @Param("restaurant")String restaurant, @Param("typeid")String typeid);










    @Select("SELECT  * FROM st_goodcommodity where  del_flag !=1 and  level > #{level}    order by level")
    List<StGoodcommodity> findStGoodcommodityBylevel(@Param("level") Integer level);



    /**
     *网上超市一级类别查询
     * @param
     * @param
     * @return
     */
    @Select("SELECT id,branchname FROM st_goodcommodity WHERE `level` = 1")
    List<Map> findStGoodcommodity();

    /**
     *网上超市二级类别查询
     * @param
     * @param
     * @return
     */
    @Select("SELECT id,branchname FROM st_goodcommodity WHERE `level` = 2 AND parent_ids = #{id}")
    List<Map> findStGoodcommodityTwo(@Param("id")String id);

    @Select("SELECT level FROM st_goodcommodity WHERE id = #{id}")
    String getLevel(@Param("id")Integer id);


    /**
     * 新增商品
     */
    boolean saveGood(StGoodcommodity stGoodcommodity);
    /**
     * 商品上下架设置
     */
    @Update("UPDATE st_goodcommodity SET isshelf = #{isshelf} WHERE id = #{id}")
    public boolean updateEnable(StGoodcommodity stGoodcommodity);

    /**
     * 入库和销售总量统计
     */
    Integer getWarehousingStatistics(String time,String id);
    /**
     * 商品分类占比统计
     */
    Integer getGoodTypeStatistics(String time,String id);
    /**
     * 入库并上架、商品修改
     */
    boolean updateWarehousing(StGoodcommodity stGoodcommodity);

    /**
     * 售卖商品列表查询
     * @param
     * @param
     * @param
     * @return
     */
    List<StGoodcommodity> findGoodListPage(@Param("start") Integer start,@Param("pageSize")Integer pageSize,@Param("name")String name,@Param("branchName")String branchName,@Param("shelfLife")String shelfLife,@Param("price") String price,@Param("inventory")String inventory);
    /**
     * 商品总记录数
     * @para
     * @param
     * @return
     */
    Integer findgoodCount(@Param("name")String name,@Param("branchName")String branchName,@Param("shelfLife")String shelfLife,@Param("price") String price,@Param("inventory")String inventory);


}
