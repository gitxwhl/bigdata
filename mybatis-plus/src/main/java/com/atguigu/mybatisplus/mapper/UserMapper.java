package com.atguigu.mybatisplus.mapper;

import com.atguigu.mybatisplus.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


@Mapper
public interface UserMapper extends BaseMapper<User> {

   Map<String,Object> selectMapById(Long id);


   /**
    * 分页
    */
   Page<User> pageVo(@Param("page") Page<User>  page,@Param("age") Integer age);
}
