package com.canteen.mapper;

import com.canteen.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    /**
     *
     * 根据id获取人员信息
     * @param
     * @return
     */
    List<Map<String, String>> getUserPerson(User user);



}
