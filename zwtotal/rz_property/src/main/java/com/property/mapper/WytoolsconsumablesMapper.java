package com.property.mapper;

import com.property.entity.Wytoolsconsumables;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface WytoolsconsumablesMapper {
    //列表数量
    int getToolConsumptionCnt(@Param("name") String name,
                              @Param("type") String type,
                              @Param("departmentId") String departmentId);

    //列表数据
    List getToolConsumptionList(@Param("startIndex")  int startIndex,
                                @Param("pageSize")  int pageSize,
                                @Param("name") String name,
                                @Param("type") String type,
                                @Param("departmentId") String departmentId);

    //新增材料及人员
    int insertMaterialsStaff(@Param("name") String name,
                             @Param("classification") String classification,
                             @Param("type") String type,
                             @Param("no") String no,
                             @Param("inventory") String inventory,
                             @Param("received") String received,
                             @Param("recipients") String recipients,
                             @Param("collectiontime") String collectiontime,
                             @Param("returntime") String returntime,
                             @Param("usenum") String usenum,
                             @Param("departmentId") String departmentId);

    //修改
    int updateMaterialsStaff(@Param("id") Integer id,
                             @Param("name") String name,
                             @Param("classification") String classification,
                             @Param("type") String type,
                             @Param("no") String no,
                             @Param("inventory") String inventory,
                             @Param("received") String received,
                             @Param("recipients") String recipients,
                             @Param("collectiontime") String collectiontime,
                             @Param("returntime") String returntime,
                             @Param("usenum") String usenum);

    @Delete("DELETE FROM wy_toolsconsumables WHERE id = #{id}")
    int deleteMaterialsStaf(String id);

    @Select("Select * FROM wy_toolsconsumables")
    List<Map<String,Object>> select();

    int insert(Wytoolsconsumables toolsconsumables);


    @Select("Select * FROM wy_toolsconsumables WHERE id = #{id}")
    Wytoolsconsumables insertDetails(@Param("id") Integer id);
}
