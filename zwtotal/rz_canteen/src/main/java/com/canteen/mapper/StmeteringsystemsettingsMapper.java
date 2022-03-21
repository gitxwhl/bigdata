package com.canteen.mapper;

import com.canteen.entity.Stmeteringsystemsettings;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface StmeteringsystemsettingsMapper {
    /**
     * 根据名称编号获取菜品类别列表
     * @param
     * @return
     */
    List<Stmeteringsystemsettings> getStmeteringsystemsettingsList(@Param("start") Integer start, @Param("size") Integer size, @Param("Dishtime") String Dishtime,
                                                                   @Param("warningWeight") String warningWeight, @Param("platedetectionTime") String platedetectionTime,
                                                                   @Param("restaurant")String restaurant);
    /**
     * 总记录数
     * @param
     * @return
     */
    Integer getStmeteringsystemsettingsCount( @Param("Dishtime") String Dishtime, @Param("warningWeight") String warningWeight,
                                              @Param("platedetectionTime") String platedetectionTime,@Param("restaurant")String restaurant);

    /**
     *停启用 1 : 启用  0:停用
     */
    @Update("UPDATE st_meteringsystemsettings SET state = #{state},synchronizationtime = now() WHERE id = #{id}")
    public boolean updateEnable(Stmeteringsystemsettings stmeteringsystemsettings);

    /**
     *输入条件可查询为空时，设置添加
     */
    @Insert("INSERT INTO st_meteringsystemsettings(Dishtime,platedetectiontime,warningweight,synchronizationtime,state,operationrestaurant,del_flag) VALUES (#{dishTime},#{platedetectionTime},#{warningWeight},now(),'0',#{operationreStaurant},#{delFlag})")
    boolean saveStmeteringsystemsettings(Stmeteringsystemsettings stmeteringsystemsettings);
    /**
     *输入条件可查询为空时，设置添加
     */
    @Select("SELECT me.Dishtime,me.platedetectiontime,me.warningweight,op.RestaurantName,me.synchronizationtime,me.state FROM st_meteringsystemsettings me LEFT JOIN st_operationrestaurant op ON me.operationrestaurant = op.id where me.platedetectiontime = #{platedetectionTime} and me.Dishtime=#{dishTime} and me.warningweight = #{warningWeight} ")
    List<Stmeteringsystemsettings> getStmeteringsystemsettings(@Param("platedetectionTime") String platedetectionTime,@Param("dishTime")String dishTime,@Param("warningWeight")String warningWeight);

    //删除设置
    void deleteById(Stmeteringsystemsettings stmeteringsystemsettings);

    //新增设置
    void addStmetering(Stmeteringsystemsettings stmeteringsystemsettings);
}