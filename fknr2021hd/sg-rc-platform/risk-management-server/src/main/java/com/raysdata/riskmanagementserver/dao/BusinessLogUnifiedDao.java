package com.raysdata.riskmanagementserver.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BusinessLogUnifiedDao {
    /**
     *添加系统日志
     */
    int saveSysLog(@Param("replace") String replace, @Param("logContent")String logContent, @Param("module")String module, @Param("logIp")String logIp, @Param("operaType")String operaType, @Param("logOn")String logOn, @Param("logUserName")String logUserName, @Param("logLevel")String logLevel, @Param("dataType")String dataType, @Param("errorType")String errorType);

    /**
     * 查询省份信息
     */
    List<Map<String,Object>> getProvinceList();

}
