package com.property.service;

import com.property.entity.Trepaircheck;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

public interface TrepaircheckService {

    Trepaircheck getTrepaircheckMapper();

}