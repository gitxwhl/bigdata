package com.officeServices.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DocumentnumberMapper {
    //获取订单编号
    List getOrderNo(@Param("type") String type);

    int updateOrderNo(@Param("no") String no,@Param("cd") String cd,@Param("type") String type);

    int insertOrderNo(@Param("no") String no,@Param("cd") String cd,@Param("type") String type);
}