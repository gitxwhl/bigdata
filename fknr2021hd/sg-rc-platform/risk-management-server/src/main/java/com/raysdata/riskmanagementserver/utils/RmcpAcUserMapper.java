package com.raysdata.riskmanagementserver.utils;

import com.raysdata.riskmanagementserver.bean.vo.SrpWorkSiteworkerinfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: Cc
 * @Date: 2021/5/31 13:44
 * @Description:
 */
@Mapper
public interface RmcpAcUserMapper {

    List<SrpWorkSiteworkerinfoVO> getUserInfoById(@Param("userId") String userId);
}
