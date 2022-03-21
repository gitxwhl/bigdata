package com.canteen.service.impl;


import com.canteen.entity.User;
import com.canteen.mapper.UserMapper;
import com.canteen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
  private   UserMapper Usermapper;


    @Override
    public List<Map<String, String>> getUserPerson(User user) {
        return Usermapper.getUserPerson(user);
    }
}
