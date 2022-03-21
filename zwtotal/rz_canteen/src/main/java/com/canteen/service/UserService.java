package com.canteen.service;

import com.canteen.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<Map<String, String>> getUserPerson(User user);

}
