package com.mstdemo.mst.mapper;

import com.mstdemo.mst.bean.Tarea;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TareaMapper {

    //获取所有父级
    @Select("SELECT id,name,code,parent_code AS parentCode,full_name AS fullName  FROM t_area WHERE parent_code IS NULL")
    List<Tarea> findParentTarea();
    //根据父级code获取子集
    @Select("SELECT id,name,code,parent_code AS parentCode,full_name AS fullName FROM t_area WHERE parent_code =#{code}")
    List<Tarea> findChildTarea(String code);
    //获取所有的地区数据
    @Select("SELECT id,name,code,parent_code AS parentCode,full_name AS fullName FROM t_area")
    List<Tarea> findTateaAll();






}
