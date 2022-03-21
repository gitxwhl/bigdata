package com.canteen.mapper;

import com.canteen.entity.StFileName;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StFileNameMapper {

    @Insert("INSERT INTO st_file_name (file_name) VALUES (#{fileName})")
    void instName(String fileName);

    @Select("SELECT file_name FROM st_file_name")
    List<String> selsctName();
}
