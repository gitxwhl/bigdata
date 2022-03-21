package com.canteen.service;

import com.canteen.entity.SystemUser;

import java.util.List;

public interface SystemUserService {
    /**
     * 登录人员基本信息体检指标
     */
    List<SystemUser> findSystemUser(SystemUser systemUser);

}
