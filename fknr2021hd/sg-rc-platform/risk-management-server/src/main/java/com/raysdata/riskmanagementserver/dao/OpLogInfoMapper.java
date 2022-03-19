package com.raysdata.riskmanagementserver.dao;

import com.raysdata.riskmanagementserver.bean.OpLogInfo;
import com.raysdata.riskmanagementserver.bean.PtUaUser;
import com.raysdata.riskmanagementserver.bean.RequestTypeInfo;
import com.raysdata.riskmanagementserver.bean.RequestTypeToOption;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OpLogInfoMapper {
    /**
     * 获取用户信息
     */
    PtUaUser findUser(@Param("useId") String useId);

    /**
     *添加系统日志
     */
    int saveOpLogInfo(@Param("opLogInfo")OpLogInfo opLogInfo);
    /**
     *日志类型插入   类型
     */
    int saveRequestTypeToOption(@Param("requestTypeToOption") RequestTypeToOption requestTypeToOption);
    /**
     *日志类型插入   描述
     */
    int saveRequestTypeInfo(@Param("requestTypeInfo") RequestTypeInfo requestTypeInfo);

}
