package com.canteen.mapper;

import com.canteen.entity.StCategorydishes;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StCategorydishesMapper {

    /**
     * 获取菜品类别列表 查询非删除数据，查询到的数据 0正常 1删除 2停用
     * @param
     * @return
     */
    @Select("SELECT  * FROM st_categorydishes where  del_flag !=1 order by level")
    List<StCategorydishes> findStCategorydishes();


    @Select("SELECT  * FROM st_categorydishes where  del_flag !=1 and  level > #{level}    order by level")
    List<StCategorydishes> findStCategorydishesBylevel(@Param("level") Integer level);
    /**
     * 根据名称编号获取菜品类别列表
     * @param
     * @return
     */
    List<StCategorydishes> getStCategorydishesList(@Param("start") Integer start, @Param("size") Integer size,@Param("varietiesName") String varietiesName,@Param("varietiesCode") String varietiesCode);
    /**
     * 总记录数
     * @param
     * @return
     */
   Integer getStCategorydishesCount(@Param("varietiesName") String varietiesName,@Param("varietiesCode") String varietiesCode);
    /**
     * 菜品类别删除，删除为逻辑删除，不能直接删除
     * @return
     */
    @Update("update  st_categorydishes  set del_flag = 1 WHERE id = #{id}")
     boolean delByIds(Integer id);

    @Update("update  st_categorydishes  set del_flag = #{delFlag} WHERE id = #{id}")
    boolean updateState(@Param("id")Integer id,@Param("delFlag")Integer delFlag);
    /**
     * 添加类别
     * @return
     */
    boolean saveStCategorydishesFist(StCategorydishes stCategorydishes);
    /**
     * 修改类别
     * @return
     */
    boolean updateStCategorydishesFist(StCategorydishes stCategorydishes);

    /**
     * 根据id获取菜品类别列表
     * @return
     */
    @Select("SELECT  * FROM st_categorydishes where id = #{id}")
    StCategorydishes findStCategorydishesbyID(Integer id);

    @Select("SELECT  count(1) FROM st_categorydishes where  del_flag !=1 and parent_ids = #{id}")
    int isExistChild(Integer id);

    List<StCategorydishes> getStCategorydishesList2(@Param("start") Integer start, @Param("size") Integer size,@Param("varietiesName") String varietiesName,@Param("varietiesCode") String varietiesCode, @Param("idsStr")String idsStr);
    Integer getStCategorydishesCount2(@Param("varietiesName") String varietiesName,@Param("varietiesCode") String varietiesCode, @Param("idsStr")String idsStr);
}