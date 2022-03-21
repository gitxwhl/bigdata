package com.canteen.mapper;

import com.canteen.entity.StEquipmentmanagement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StEquipmentmanagementMapper {
    //获取设备列表
    List getEquipmentList(@Param("startIndex")int startIndex,@Param("pageSize")int pageSize,
                          @Param("restaurantName")String restaurantName,@Param("equipmentName")String equipmentName,
                          @Param("equipmentCode")String equipmentCode);

    //查询设备数量
    Integer getEquipmentCnt(@Param("restaurantName")String restaurantName,@Param("equipmentName")String equipmentName,
                            @Param("equipmentCode")String equipmentCode);

    //删除设备
    Integer deleteEquipment(@Param("id")String id);

    //新增设备
    Integer insertEquipment(StEquipmentmanagement stEquipmentmanagement);

    //修改设备
    Integer updateEquipment(StEquipmentmanagement stEquipmentmanagement);

    //通过id查询设备
    List selectEquipmentById(@Param("id")int id);
}
