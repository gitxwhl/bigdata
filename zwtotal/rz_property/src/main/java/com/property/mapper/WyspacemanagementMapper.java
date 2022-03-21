package com.property.mapper;

import com.property.entity.Wyspacemanagement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface WyspacemanagementMapper {

    //查询列表数量
    int getWyspacemanagementCnt(@Param("suppliername") String suppliername,
                                @Param("attr1") String attr1);
    //查询列表数据
    List getWyspacemanagementList(@Param("startIndex")  int startIndex,
                                  @Param("pageSize")  int pageSize,
                                  @Param("suppliername")  String suppliername,
                                  @Param("attr1")  String attr1);
    //查询植被列表数量
    int getvegetationtCnt(@Param("id") String id);

    //查询植被数据
    List getvegetationtList(@Param("startIndex")  int startIndex,
                            @Param("pageSize")  int pageSize,
                            @Param("id") String id);
    //新增供应商信息
    int getSupplierManagement(Wyspacemanagement wyspacemanagement);


//------------------------------------------------------空间管理-------------------------
//获取空间总数
    int getTotalSize(@Param("corporatid") String corporatid,@Param("name") String name);

    //获取空间信息
    List getSpace(@Param("corporatid") String corporatid,@Param("name") String name,
                  @Param("startIndex")int startIndex,@Param("pageSize")int pageSize);

    //空间信息修改
    Map updateSpaceById(@Param("id") Integer id);
    void updateSpace(@Param("id") int id, @Param("spacename") String spacename,
                     @Param("builtarea") String builtarea, @Param("greeningratio")String greeningratio,
                     @Param("detailedaddress")String detailedaddress, @Param("description")String description,
                     @Param("enclosure")String enclosure,@Param("corporatid") int corporatid);

    //空间信息删除
    void deleteSpace(@Param("id") Integer id);

    //新增空间信息
    void insertSpace(@Param("spacename") String spacename,@Param("builtarea") String builtarea,
                     @Param("greeningratio")String greeningratio,@Param("detailedaddress")String detailedaddress,
                     @Param("description")String description,@Param("enclosure")String enclosure,
                     @Param("corporatid")int corporatid);

    //查看附件
    String getSpaceFile(@Param("id") int id);
}