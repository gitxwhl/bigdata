package com.property.mapper;

import com.property.entity.Wyspace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface WyspaceMapper {

    //保洁区域数量
    Integer getCleanSpaceCnt(@Param("spaceName")String spaceName);

    //保洁区域列表
    List getCleanSpace(@Param("spaceName")String spaceName,@Param("index")Integer index,@Param("pageSize")Integer pageSize);

    //通过设备id查询设备名称
    List getEquipmentById(@Param("ids")String ids);

    //新增保洁区域
    Integer addCleanSpace(Wyspace wyspace);

    //修改保洁区域
    Integer updateCleanSpace(Wyspace wyspace);

    //删除保洁区域
    Integer deleteCleanSpace(Wyspace wyspace);

    //查询可关联的设备
    List getEquipment();

    //通过设备名称查询设备id
    List getEquipmentId(@Param("name")String name);

    //通过id查询附件
    String getEnclosureById(@Param("id")Integer id);
}