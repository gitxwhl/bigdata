package com.canteen.mapper;

import com.canteen.entity.SystemUser;
import org.apache.ibatis.annotations.*;
import java.util.List;
@Mapper
public interface SystemUserMapper {

    /**
     * 登录人员基本信息体检指标
     */
    List<SystemUser> findSystemUser(SystemUser systemUser);

}
