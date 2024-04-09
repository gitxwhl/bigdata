package com.bobo.service.impl;
import com.bobo.dao.UserMapper;
import com.bobo.pojo.SysUser;
import com.bobo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    /**
     * 根据账号查询用户信息
     * @param userName 账号
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // 根据账号查询
        SysUser sysUser = userMapper.queryUserByUsername(userName);
        if(sysUser != null){
            List<GrantedAuthority> list = new ArrayList<>();
            list.add(new SimpleGrantedAuthority("ROLE_USER"));
            // 表示账号存在
            UserDetails userDetails = new User(
                    sysUser.getUserName() // 账号
                    , sysUser.getPassword() // 密码
                    ,list
                    );
            return userDetails;
        }
        // 返回null 表示账号不存在
        return null;
    }

    public static void main(String[] args) {
        //注册时候加密可以使用这个，然后，获取加密过后的数据之后入库
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        String password="admin";
        System.out.println("encoder.encode(password) ="+encoder.encode(password));
        System.out.println("encoder.encode(password) ="+encoder.encode(password));
        System.out.println("encoder.encode(password) ="+encoder.encode(password));
        System.out.println("encoder.encode(password) ="+encoder.encode(password));

    }
}
