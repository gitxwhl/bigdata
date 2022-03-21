package com.property.mapper;

import com.property.entity.Trepaircheck;
import com.property.entity.Ufrepair;
import com.property.entity.Wycorporate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UfrepairMapper {
    //查询部门导航栏
    List<Wycorporate> getWycorporateList(@Param("id") Integer id, @Param("parentIds")String parentIds);

    //报修申请
    Integer addUfrepair(Ufrepair ufrepair);

    //报修人查询报修记录
    List selectRecord(@Param("mBxrgh")String mBxrgh,@Param("state")String state,@Param("num")String num,
                      @Param("time")String time,@Param("index")Integer index,@Param("pageSize")Integer pageSize);
    //报修人查询报修记录数量
    Integer selectRecordCnt(@Param("mBxrgh")String mBxrgh,@Param("state")String state,
                            @Param("num")String num,@Param("time")String time);

    //管理员维修管理
    List management(@Param("state")String state,@Param("num")String num,@Param("name")String name,
                    @Param("time")String time,@Param("index")Integer index,@Param("pageSize")Integer pageSize);
    //管理员维修管理列表数量
    Integer managementCnt(@Param("state")String state,@Param("num")String num,
                          @Param("name")String name, @Param("time")String time);

    //管理员派单
    void sendOrders(Ufrepair ufrepair);

    //完成维修
    Integer finishWx(Ufrepair ufrepair);

    //报修评价
    Integer updateRepair(Ufrepair ufrepair);

    //报修类型分析
    //List getRepairType(@Param("timeType") Integer timeType);

    Integer getCount(@Param("state")String state,@Param("timeType")Integer timeType);

    //按评分分类统计
    Map getStatistics();

    //按部门统计次数
    List getCountByBm();

    //报修设备查询
    List getEquipment();

    //通过id查询设备信息
    Map getEquipmentById(@Param("id")String id);

}