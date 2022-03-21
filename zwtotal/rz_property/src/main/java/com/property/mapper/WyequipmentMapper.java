package com.property.mapper;

import com.property.entity.Wyequipment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Mapper
public interface WyequipmentMapper {
    //获取总数量
    int getTotalSize(@Param("company") String company, @Param("name") String name);
    //获取设备台账信息
    List<Map> selectEquipment(@Param("company") String company, @Param("name") String name,
                              @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    //添加设备台账信息
    void insertEquipment(Wyequipment stEquipment);

    //查询修改设备信息:存在安全风险方法隐藏
//    Map selectEquipmentById(@Param("id") Integer id);

    //修改设备
    void updateEquipment(Wyequipment stEquipment);

    //删除设备台账
    void deleteEquipment(@Param("id") Integer id);


    //查询列表数量
    int getWyEquipmentCnt(@Param("name") String name,
                          @Param("code") String code,
                          @Param("department") String department);

    //查询基本信息
    List getWyEquipmentList(
                            @Param("name") String name,
                            @Param("code") String code,
                            @Param("department") String department);


    int getWyEquipmentCnt1(@Param("id") int id);

    List getWyEquipmentList1(@Param("startIndex") int startIndex,
                             @Param("pageSize") int pageSize,
                             @Param("id") int id);

    //生成二维码--根据id查询信息：存在安全风险方法隐藏
//    Map getCode(@Param("id") Integer id);

}