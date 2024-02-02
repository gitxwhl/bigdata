package com.msb.service.impl;
import com.msb.mapper.UserMapper;
import com.msb.pojo.User;
import com.msb.service.UserService;
import com.msb.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    /**
     * 分页查询数据
     * @return
     */
    @Override
    public PageBean<User> findUser(User user, Integer pageNum, Integer pageSize) {
        PageBean page=new PageBean();
        //当前页
        page.setPageNum(pageNum);
        //当前也数量
        page.setPageSize(pageSize);
        //获取总数
        int count = userMapper.getcount(user);
        page.setTotalRecord(count);
        //分页查询列表
        if(count > 0){
            List<User> userList = userMapper.findUser(user,(pageNum-1)*pageSize,pageSize);
            page.setList(userList);
        }else {
            page.setList(new ArrayList<User>());
        }
        return page;
    }
}
