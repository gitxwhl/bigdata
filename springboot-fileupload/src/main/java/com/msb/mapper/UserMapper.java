package com.msb.mapper;

import com.msb.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    //查询用户分页数据
    List<User> findUser(@Param("user") User user,@Param("start") Integer start, @Param("pageSize") Integer pageSize);
    //查询总的记录数
    @Select("select count(*) from sys_user")
    int getcount(User user);

}
