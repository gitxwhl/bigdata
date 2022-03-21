package com.property.mapper;

import com.property.entity.Trepaircheck;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TrepaircheckMapper {

    @Select("SELECT * from t_repair_check")
    Trepaircheck getTrepaircheckMapper();

}