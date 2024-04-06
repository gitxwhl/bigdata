package com.bobo.dao;

import com.bobo.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    public SysUser queryUserByUsername(String username);
}
