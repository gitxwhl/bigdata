package com.canteen.mapper;

import com.canteen.entity.StCategorydishes;
import com.canteen.entity.StMydishstock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StMydishstockMapper {

    /**
     * 查询物品数据库库存
     * @param
     * @return
     */
    @Select("SELECT  * FROM st_mydishstock where  dis_id = #{disId}")
    StMydishstock getStMydishstockByDisId(@Param("disId")int disId);

    /**
     * 下单结算前锁定库存 ，可用用库-num ,锁定库存+num
     * @param id 库存id
     * @param num  锁定库存
     * @return
     */
    @Update("update  st_mydishstock  set ava_stock = (ava_stock - #{num}) ,lock_stock = (lock_stock + #{num}) WHERE id = #{id} and ava_stock>=#{num}")
    boolean updateStock(@Param("id") Integer id, @Param("num") Integer num);
   }