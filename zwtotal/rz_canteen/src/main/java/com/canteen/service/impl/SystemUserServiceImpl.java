package com.canteen.service.impl;

import com.canteen.entity.SystemUser;
import com.canteen.mapper.SystemUserMapper;
import com.canteen.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemUserServiceImpl implements SystemUserService {

    @Autowired
     private SystemUserMapper systemUserMapper;

    /**
     * 登录人员基本信息体检指标
     */
    @Override
    public List<SystemUser> findSystemUser(SystemUser systemUser) {
        return systemUserMapper.findSystemUser(systemUser);
    }
}
