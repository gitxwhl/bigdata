package com.msb.service;
import com.msb.pojo.User;
import com.msb.util.PageBean;
public interface UserService {
    //分页查询
    PageBean<User> findUser(User user,Integer pageNum,Integer pageSize);
}
